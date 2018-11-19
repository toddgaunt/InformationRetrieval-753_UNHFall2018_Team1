package cs753.T1.A5;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.search.similarities.BasicStats;
//import org.apache.lucene.search.similarities.SimilarityBase;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;
public class App 
{
	static long number_of_terms = 0;
	
	public static void usage()
	{
		System.out.println("usage: make run PARA=path/to/paragraphs OUTLINE=path/to/outlines METHOD=<lnc.ltn|bnn.bnn|anc.apc>");
		System.exit(-1);
	}
	
	public static String getQueryRFF(IndexSearcher is, String pageID, String query, Similarity method) throws Exception {
		int rank = 1;
		String ret = "";

		QueryParser parser = new QueryParser("content", new StandardAnalyzer());
		TopDocs results;
		ScoreDoc[] hits;

		is.setSimilarity(method);

		results = is.search(parser.parse(query), 100);
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
		return ret;
	}
	
	public static void main(String[] args)
	{
		try {
			String dataFile;
			String outline;
			Similarity method = null;

			if (args.length != 3)
				usage();
			dataFile = args[0];
			outline = args[1];

			/* Setup the indexer */
			Directory indexDir = FSDirectory.open(new File("index").toPath());
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			IndexWriter iwriter = new IndexWriter(indexDir, config);

			/* Deserialize the data and add documents to the index */
			FileInputStream fp_para = new FileInputStream(dataFile);
			for (Data.Paragraph para : DeserializeData.iterableParagraphs(fp_para)) {
				Document doc = new Document();
				doc.add(new StringField("id", para.getParaId(), Field.Store.YES));
				doc.add(new TextField("text", para.getTextOnly(), Field.Store.YES));
				iwriter.addDocument(doc);
			}
			iwriter.close();
			fp_para.close();
			/* Use the index */
			IndexReader ir = DirectoryReader.open(FSDirectory.open(new File("index").toPath()));
			IndexSearcher is = new IndexSearcher(ir);
			
			PrintWriter outfile = new PrintWriter("out.runfile", "UTF-8");
			FileInputStream fp_outline = new FileInputStream(outline);
			for (Data.Page page : DeserializeData.iterableAnnotations(fp_outline)) {
				outfile.print(getQueryRFF(is, page.getPageId(), "text: " + page.getPageName(), method));
			}
			fp_outline.close();
			outfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
