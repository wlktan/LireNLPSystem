package edu.uw.biostat.lire.RuleBasedNLP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RuleBasedNLP{

	public static ArrayList<String> negationPatterns = new ArrayList<String>();
	public static List<List<String>> errorArray = new ArrayList<List<String>>();
	public static final String ENCODING = "UTF-8";
	public static final String DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	public static final String separator = "\t";
	public static final String[] neut = {"0", "0"};
	public static final String[] pos = {"1", "0"};
	public static final String[] neg = {"1", "1"};
	public static final int MAX_WINDOW = 15;

	public String SayHello(String username){
		String result = new String("Hello, my friend " + username + "!");
		return result;
	}

	public double AddTwoNumbers(double a, double b){
		double sum = a + b;
		return sum;
	}

	public String[][] GetRegexNegex(String[][] inputDF,
			String imageid,
			String siteID,
			String imageTypeID,
			String bodyText,
			String impressionText) throws Exception{

		/* 2D Array to ArrayList conversion */
		List<List<String>> df = new ArrayList<List<String>>();
		for (String[] row : inputDF) {
			List<String> rowList = Arrays.asList(row);
			df.addAll(Arrays.asList(rowList));
		}
		
		/* Initialize KeywordDictionary class of regular expressions for all keywords */
		KeywordDictionary keywords = new KeywordDictionary();
		keywords.initialize();
		Map<String, String[]> FindingListKeywords = (Map<String, String[]>) keywords;
		System.out.println("Keyword dictionary loaded for " + FindingListKeywords.size() + " findings."); 

		/*Initialize context file using the context.csv input downloaded from fastcontext GitHub page 
		 * Please see: https://github.com/jianlins/FastContext */
		String contextFile = "context.csv";

		//Get file from resources folder
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(contextFile);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String line;
		while ((line = br.readLine()) != null) 
		{
			negationPatterns.add(line);
		}
		br.close();
		isr.close();
		is.close();
		System.out.println("Loaded " + contextFile + " with "+ negationPatterns.size() + " negation patterns.");

		/* Run NLP algorithm */
		errorArray = new ArrayList<List<String>>();
		ProcessReport textProcesser = new ProcessReport();	
		textProcesser.getRegex(df, 
				imageid, 
				siteID, 
				imageTypeID, 
				bodyText, 
				impressionText,
				FindingListKeywords);
		
		//System.out.println(errorArray.size());
/*		for(List<String> row: errorArray){
			System.out.println(row);
		}*/

		/* 2D ArrayList to Array conversion */	
		String[][] outputDF = new String[errorArray.size()][];
		int i = 0;
		for (List<String> row : errorArray){
			outputDF[i++] = row.toArray(new String[row.size()]);
		}
			
		return outputDF;  
	}

	public static void main(String[] args) throws Exception{
		// to test the algorithm
/*		String[][]in = {{"imageid", "W131", "W122", "W21442"},
				{"siteID", "1", "2", "3"},
				{"imageTypeID", "3", "3", "1"},
				{"findings", "No disc bulge", "There is some stenosis.", "Unremarkable spine. Testing for some weirdness degeneration."},
				{"impression", "More test for fracture", "Nonsense stenosis", "Normal"}};
				
		String imageid = "imageid";
		String siteID = "siteID";
		String imageTypeID = "imageTypeID";
		String bodyText = "findings";
		String impressionText = "impression";

		String[][] test = GetRegexNegex(in);
		System.out.println(test.length);
		for(int i=0; i<test.length; i++){
			for(int j=0; j<test[i].length; j++){
				System.out.print(test[i][j] + " ");
			}
			System.out.println("");
		}*/

	}
}

