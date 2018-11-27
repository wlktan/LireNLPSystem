package edu.uw.biostat.lire.RuleBasedNLP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RuleBasedNLP{

	public static ArrayList<String> negationPatterns = new ArrayList<String>();
	public static ArrayList<String> fileStopWords = new ArrayList<String>();
	public static List<List<String>> outputArray = new ArrayList<List<String>>();
	public static final String ENCODING = "UTF-8";
	public static final String DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	public static final String separator = "\t";
	public static final String[] neut = {"0", "0"};
	public static final String[] pos = {"1", "0"};
	public static final String[] neg = {"1", "1"};
	public static final int MAX_WINDOW = 15; // Number of spacings between negation and regular expression in a sentence

	public String[][] GetRegexNegex(String[][] inputDF,
			String imageid,
			String bodyText,
			String impressionText,
			String findings_longstring) throws Exception{

		/* Convert 2D Array --> ArrayList (due to the rJava interface requiring Array
		 * and cannot use ArrayList) */
		List<List<String>> df = new ArrayList<List<String>>();
		for (String[] row : inputDF) {
			List<String> rowList = Arrays.asList(row);
			df.addAll(Arrays.asList(rowList));
		}
		List<String> findings_list = Arrays.asList(findings_longstring.split(";"));
		
		/* Initialize KeywordDictionary class of regular expressions for all keywords */
		KeywordDictionary keywords = new KeywordDictionary();
		keywords.initialize();
		Map<String, String[]> FindingListKeywords = (Map<String, String[]>) keywords;
		
		/* Subset of KeywordDictionary in array_of_findings */
		List<String> to_be_removed = new ArrayList<String>();
		for(int f=0; f<FindingListKeywords.size(); f++){ // do for every finding
			to_be_removed.add(FindingListKeywords.keySet().toArray()[f].toString());
		}
		to_be_removed.removeAll(findings_list);
		
		for(String key : to_be_removed) {
			FindingListKeywords.remove(key);
		}
		System.out.println("Keyword dictionary loaded for " + FindingListKeywords.size() + " findings."); 
						
		/*Initialize context file using the context.csv input downloaded from fastcontext GitHub page 
		 * Please see: https://github.com/jianlins/FastContext */
		String contextFile = "context.csv";
		while(negationPatterns.size() == 0) {
			//Get file from resources folder
			ClassLoader loadConText = Thread.currentThread().getContextClassLoader();
			InputStream isConText = loadConText.getResourceAsStream(contextFile);
			InputStreamReader isrConText = new InputStreamReader(isConText);
			BufferedReader brConText = new BufferedReader(isrConText);
			
			String ConTextline;
			while ((ConTextline = brConText.readLine()) != null) 
			{
				negationPatterns.add(ConTextline);
			}
			brConText.close();
			isrConText.close();
			isConText.close();
		}
		System.out.println("Loaded " + contextFile + " with "+ negationPatterns.size() + " negation patterns.");
		
		/* Loads the stopwords file (Only if empty) */
		String stopwordsFile = "stopwords.txt";
		while(fileStopWords.size() == 0) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream is = cl.getResourceAsStream(stopwordsFile);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) 
			{
				fileStopWords.add(line);
			}
			br.close();
			isr.close();
			is.close();
		}
		System.out.println("Loaded " + stopwordsFile + " with "+ fileStopWords.size() + " stopwords.");
		
		/* Run NLP algorithm */
		outputArray = new ArrayList<List<String>>();
		ProcessReport textProcesser = new ProcessReport();	
		textProcesser.getRegex(df, 
				imageid, 
				bodyText, 
				impressionText,
				FindingListKeywords);

		/* Convert 2D ArrayList --> Array */
		String[][] outputDF = new String[outputArray.size()][];
		int i = 0;
		for (List<String> row : outputArray){
			outputDF[i++] = row.toArray(new String[row.size()]);
		}
			
		return outputDF;  
	}
}

