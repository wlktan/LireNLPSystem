package edu.uw.biostat.lire.RuleBasedNLP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessReport {

	// Constants for FeatureGenerator class
	
	static StopWords sw = new StopWords();

	// Constructor for FeatureGenerator class
	public ProcessReport() {

	}
	
	
	void getRegex(List<List<String>> df,
			String imageid, 
			String siteID, 
			String imageTypeID, 
			String bodyText,
			String impressionText, 
			Map<String, String[]> FindingListKeywords) throws Exception{
		
		List<String> headerNames = new ArrayList<String>();
		for(int i=0; i<df.size(); i++){
			headerNames.add(df.get(i).get(0));
		}
		
				Regex rbnlp = new Regex();
				
				for(int f=0; f<FindingListKeywords.size(); f++){ // do for every finding
					String finding = FindingListKeywords.keySet().toArray()[f].toString();
					
					for(int report=1; report<df.get(0).size(); report++){
						/*Processes body text */
						rbnlp.process(df.get(headerNames.indexOf(imageid)).get(report),
								df.get(headerNames.indexOf(siteID)).get(report),
								df.get(headerNames.indexOf(imageTypeID)).get(report),
								df.get(headerNames.indexOf(bodyText)).get(report),
								finding,
								FindingListKeywords.get(finding),
								RuleBasedNLP.neut, 
								RuleBasedNLP.pos, 
								RuleBasedNLP.neg,
								"body");
						
						/*Processes impression text */					
						rbnlp.process(df.get(headerNames.indexOf(imageid)).get(report),
								df.get(headerNames.indexOf(siteID)).get(report),
								df.get(headerNames.indexOf(imageTypeID)).get(report),
								df.get(headerNames.indexOf(impressionText)).get(report),
								finding,
								FindingListKeywords.get(finding),
								RuleBasedNLP.neut, 
								RuleBasedNLP.pos, 
								RuleBasedNLP.neg,
								"impression");
					}
					
					
				}

	}	
}
