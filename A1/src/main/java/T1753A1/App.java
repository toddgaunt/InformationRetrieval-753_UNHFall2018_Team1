package T1753A1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.CompositeReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

public class App 
{
	public static void main(String[] args)
	{
		try {
			//if (args.length != 2) {
			//	System.out.println("Usage: A1 <document to index>");
			//	return;
			//}
			//String dataFile = args[1];
			String dataFile = "data/test200/test200-train/fold-0-train.pages.cbor-paragraphs.cbor";
			/* Setup the indexer */
			
			Directory indexDir = FSDirectory.open(new File("index").toPath());
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			IndexWriter iwriter = new IndexWriter(indexDir, config);
			
	
			/* Deserialize the data and add documents to the index */
			FileInputStream fp = new FileInputStream(dataFile);
			for (Data.Paragraph para : DeserializeData.iterableParagraphs(fp)) {
				Document doc = new Document();
				doc.add(new StringField("id", para.getParaId(), Field.Store.YES));
				doc.add(new TextField("text", para.getTextOnly(), Field.Store.YES));
				iwriter.addDocument(doc);
			}
			iwriter.close();
			/* Use the index */
			IndexSearcher is = new IndexSearcher(DirectoryReader.open(indexDir));
			QueryParser parser = new QueryParser("content", new StandardAnalyzer());
			TopDocs results = is.search(parser.parse("power nap benefits"), 10);
			
			/* TODO(todd): Modify this to print out a list of the results */
			System.out.println(results);
			/* End TODO */
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
