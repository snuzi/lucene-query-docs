
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class MyQuery {

	private Directory _dir;
	private Analyzer analyzer;
	private IndexReader reader;
	private IndexSearcher searcher;
	
	public MyQuery(String dirPath)
	{
		// Open the directory where the index is located
		try { 
			Path path=Paths.get(dirPath);
			_dir = FSDirectory.open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Define the analyzer type you want to uses
		analyzer = new StandardAnalyzer();

		// Create the index reader
		try {
			reader = DirectoryReader.open(_dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the index reader
		searcher = new IndexSearcher(reader);
	}
	
	private TopDocs query(String queryString, int limit)
	{
		TopDocs scoreDocs = null;
		String[] documentFields = {"Title", "Author", "Content"};
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(documentFields, analyzer);
		
		try {
			scoreDocs = searcher.search(queryParser.parse(queryString), limit);
		} catch (IOException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		return scoreDocs;
	}
	
	public void query(String queryString, int limit)
	{
		TopDocs foundDocuments = query(queryString,limit);
		
		printResult(foundDocuments,limit);
	}

	private void printResult(TopDocs scoreDocs, int limit)
	{
		Document doc = null;
		String title;
		String name;
		System.out.println(scoreDocs.totalHits + " results found");
		System.out.println();

		int numdocs = scoreDocs.totalHits;
		if(numdocs < limit) {
			limit = numdocs;
		}

		for (int i = 0; i < limit; i++) {
			try {
				doc = searcher.doc(scoreDocs.scoreDocs[i].doc);
				title = doc.getField("Title").stringValue();
				System.out.println((i+1)+"- "+title+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeQuery()
	{
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

