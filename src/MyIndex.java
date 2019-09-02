import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MyIndex {

	private Directory indexDirectory;
	private IndexWriterConfig indexWriterConfig;
	private Analyzer analyzer;
	private IndexWriter indexWriter = null;
	
	public MyIndex(String dirPath) 
	{
		// Create the directory where indexed data will be stored
		try {
			Path path = Paths.get(dirPath);
			indexDirectory = FSDirectory.open(path);
			indexDirectory = FSDirectory.open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Define the analyzer type you want to use
		analyzer = new StandardAnalyzer();

		// Define the configuration parameter
		indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE);
		//Initialize the index writer
		try 
		{
			indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Build the index
		String path = "src/index";
		String files = "docs/txt_content/test1/";
		MyIndex myIdx = new MyIndex(path);
		
		File folder = new File(files);
		File[] allFiles = folder.listFiles();
	
		StringBuffer content;
		String extraContent="";
		
		for (File file: allFiles) {
			String name=file.getName();
			name = name.split(".txt")[0];
			content = new StringBuffer("");
			content.append(FileUtils.readFileToString(file));
			myIdx.addDoc(name, content.toString(),extraContent);
			System.out.println(name);
			System.out.println(content);
			System.out.println();
		}
		
		myIdx.closeIndex();
	}
	
	public void addDoc(String title, String content, String extraContent)
	{
		Document document = null;
		Field titleField = null;  
		Field contentField = null;
		Field extraContentField = null;
		
		document = new Document();
		
		titleField = new TextField("Title", title, Field.Store.YES);
		titleField.setBoost(MyEngine.BOOST_TITLE);
		document.add(titleField);		
		
		contentField = new TextField("Content", content, Field.Store.NO);
		contentField.setBoost(MyEngine.BOOST_CONTENT);
		document.add(contentField);
		
		extraContentField = new TextField("extraContent", extraContent, Field.Store.NO);
		extraContentField.setBoost(MyEngine.BOOST_EXTRA_CONTENT);
		document.add(extraContentField);
		
		try {
			indexWriter.addDocument(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeIndex()
	{
		try {
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
