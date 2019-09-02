
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class MyEngine {

	public static int BOOST_TITLE = 2;
	public static int BOOST_CONTENT = 3;	
	public static int BOOST_EXTRA_CONTENT = 2;
	
	public static void main(String[] args) throws IOException {
		
		String path = "src/index";		
		String query = "";
		
		while (!query.equals("-1")) {
			System.out.println("Write your query:");
			Scanner in = new Scanner(System.in);
			
			query = in.nextLine();
			
			MyQuery myQuery = new MyQuery(path);
			myQuery.query(query, 10);
			myQuery.closeQuery();
			System.out.println("\n *************************----------------------************************ (-1 to quite) \n");		
		}
	}
	
	public static float getDocBoost(int document)
	{
		switch (document) {
			case 1:
				return 1.1f;
			case 2:
				return 1.2f;
			case 3:
				return 1f;
			case 4:
				return 1.2f;
			case 5:
				return 1.1f;
			case 6:
				return 1.2f;
			case 7:
				return 1.1f;
			case 8:
				return 1.5f;
			case 9:
				return 1.7f;
			case 10:
				return 1.4f;
			case 11:
				return 1f;
			case 12:
				return 1f;
			case 13:
				return 1f;
			case 14:
				return 1.1f;
			case 15:
				return 1f;
			case 16:
				return 1.2f;
			case 17:
				return 1f;
			case 18:
				return 1f;
			case 19:
				return 1.1f;
			case 20:
				return 1f;
			default:
				return 1.1f;
		}
	}
}
