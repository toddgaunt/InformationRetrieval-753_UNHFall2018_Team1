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
		System.out.println("usage: make run QUESTION=<Q1|Q2> PARA=path/to/paragraphs OUTLINE=path/to/outlines");
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
	
	public static void question1() {
		Integer qid = 1;
		Rank D1 = new Rank("D1", 0);
		Rank D2 = new Rank("D2", 1);
		Rank D3 = new Rank("D3", 1);
		Rank D4 = new Rank("D4", 0);
		Rank D5 = new Rank("D5", 1);
		Rank D6 = new Rank("D6", 0);
		Rank D7 = new Rank("D7", 0);
		Rank D8 = new Rank("D8", 0);
		Rank D9 = new Rank("D9", 0);
		Rank D10 = new Rank("D10", 0);
		Rank D11 = new Rank("D11", 0);
		Rank D12 = new Rank("D12", 0);
		
        Rank rankList1[] = {D1, D2, D3, D4, D5, D6};
        Rank rankList2[] = {D2, D5, D6, D7, D8, D9, D10, D11};
        Rank rankList3[] = {D1, D2, D5};
        Rank rankList4[] = {D1, D2, D8, D10, D12};
        Rank rankingsList[][] = {rankList1, rankList2, rankList3, rankList4};
		
        String rlf = Rank.rankLibFmtFromRanklist(qid, rankingsList);
		System.out.println(rlf);
	}
	
	public static void question2(String[] args) throws Exception {
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
	}
	
	public static void main(String[] args)
	{
		try {
			if (args.length < 1) {
				usage();
			}
			if (args[0].equals("Q1")) {
				question1();
			} else if (args[0].equals("Q2")) {
				question2(args);
			} else {
				usage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
