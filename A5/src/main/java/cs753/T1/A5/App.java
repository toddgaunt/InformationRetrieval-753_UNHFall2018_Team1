package cs753.T1.A5;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
//import org.apache.lucene.index.IndexReader;
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
		System.out.println("usage: make run PARA=path/to/paragraphs OUTLINE=path/to/outlines");
		System.exit(-1);
	}

	public static Rank[] getQueryRFF(IndexSearcher is, String query, Similarity method) throws Exception {
        int rank = 0;
	    Rank[] ranks = new Rank[10];

		QueryParser parser = new QueryParser("content", new StandardAnalyzer());
		TopDocs results;
		ScoreDoc[] hits;

		is.setSimilarity(method);

		results = is.search(parser.parse(query), 10);
		hits = results.scoreDocs;
		for (ScoreDoc hit: hits) {
			Document doc = is.doc(hit.doc);
			ranks[rank] = new Rank(doc.get("id"), 0);
			rank +=1;
		}
		return ranks;
	}

	
	public static void question2(String[] args) throws Exception {
	    String dataFile;
        String outline;
        int queryID = 0;
		Rank[][] listOfRanks = new Rank[5][10];
        TFIDF tfidf = new TFIDF();
        LM lm = new LM();

		if (args.length != 2)
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

        Set<String> s = new HashSet<String>();
        for (int i = 0; i < ir.numDocs(); i++) {
            for (IndexableField j: ir.document(i).getFields())
            {
                s.add(j.name());
            }
        }
        number_of_terms = s.size();


        PrintWriter outfile = new PrintWriter("out.runfile", "UTF-8");
        FileInputStream fp_outline = new FileInputStream(outline);
        for (Data.Page page : DeserializeData.iterableAnnotations(fp_outline)) {
            listOfRanks[0] = getQueryRFF(is, "text: " + page.getPageName(), tfidf.LncLtnSim());
            listOfRanks[1] = getQueryRFF(is, "text: " + page.getPageName(), tfidf.BnnBnnSim());
            listOfRanks[2] = getQueryRFF(is, "text: " + page.getPageName(), lm.U_L(number_of_terms));
            listOfRanks[3] = getQueryRFF(is, "text: " + page.getPageName(), lm.U_JM());
            listOfRanks[4] = getQueryRFF(is, "text: " + page.getPageName(), lm.U_DS());
            outfile.print(Rank.rankLibFmtFromRanklist(queryID, listOfRanks));
            queryID+=1;
        }
        fp_outline.close();
        outfile.close();
	}
	
	public static void main(String[] args)
	{
		try {
			question2(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
