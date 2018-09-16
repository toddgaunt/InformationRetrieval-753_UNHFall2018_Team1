package cs753.T1.A2;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void usage()
	{
		System.out.println("usage: A2 file");
		System.exit(-1);
	}
	
	public static void printQuery(IndexSearcher is, String query) {
		try {
		QueryParser parser = new QueryParser("content", new StandardAnalyzer());
		TopDocs results;
		ScoreDoc[] hits;
		
		/* Perform the first query */
		results = is.search(parser.parse(query), 10);
		hits = results.scoreDocs;
		System.out.println("Begin: '" + query + "'");
		for (ScoreDoc hit: hits) {
			Document doc = is.doc(hit.doc);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("text"));
		}
		System.out.println("End '" + query +"'");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

    public static void main(String[] args)
    {
    	try {
			String dataFile;
			String queryFile;
			FileInputStream fp;

			if (args.length != 2)
				usage();
			dataFile = args[0];
			queryFile = args[1];
			
			/* Setup the indexer */
			Directory indexDir = FSDirectory.open(new File("index").toPath());
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			IndexWriter iwriter = new IndexWriter(indexDir, config);
			
	
			/* Deserialize the data and add documents to the index */
			fp = new FileInputStream(dataFile);
			for (Data.Paragraph para : DeserializeData.iterableParagraphs(fp)) {
				Document doc = new Document();
				doc.add(new StringField("id", para.getParaId(), Field.Store.YES));
				doc.add(new TextField("text", para.getTextOnly(), Field.Store.YES));
				iwriter.addDocument(doc);
			}
			iwriter.close();
			fp.close();
			/* Use the index */
			IndexSearcher is = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("index").toPath())));
			
			fp = new FileInputStream(queryFile);
			for (Data.Page page : DeserializeData.iterableAnnotations(fp)) {
				System.out.println(page.getPageId());
			}
			fp.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
