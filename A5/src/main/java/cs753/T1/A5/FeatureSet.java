package cs753.T1.A5;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;

class RankList {
}

public class FeatureSet {
	public List<Similarity> f;
	
	public FeatureSet() {
		f = new ArrayList<Similarity>();
	}
	
	public void add(Similarity s) {
		f.add(s);
	}

	public RankList queryRank(IndexSearcher is, String query_string) {
		int rank = 1;
		String ret = "";

		QueryParser parser = new QueryParser("content", new StandardAnalyzer());
		TopDocs results;
		ScoreDoc[] hits;
		for (Similarity s : f) {
			is.setSimilarity(s);
			Query query = parser.parse(query_string);
			results = is.search(query, 100);
			hits = results.scoreDocs;
			for (ScoreDoc hit: hits) {
				Document doc = is.doc(hit.doc);
				ret += pageID;
				ret += " Q0";
				ret += " " + doc.get("id");
				ret += " " + rank;
				ret += " " + hit.score;
				ret += " team1-default";
				ret += "\n";
				rank += 1;
			}
		}
	}
}