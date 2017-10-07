package edu.uw.biostat.lire.RuleBasedNLP;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.utah.bmi.nlp.fastcontext.FastContext;

public class Regex {

	// default constructor for regex class
	public Regex() {

	}

	// pipeline to process regular expression: REGEX, NEGEX at the section level
	public void process(String imageid, 
			String siteID, 
			String imageTypeID,
			String text, 
			String finding, 
			String[] keywords, 
			String[] neut, 
			String[] pos, 
			String[] neg,
			String section_of_sentence) throws Exception {
		
		if(text == null) text = "NA"; // Turns null to a String
		
		BreakIterator splitIntoSentences = BreakIterator.getSentenceInstance();
		splitIntoSentences.setText(text);
		int index = 0;

		while (splitIntoSentences.next() != BreakIterator.DONE) {
			String sentence = text.substring(index, splitIntoSentences.current());
			RegexSentenceLevel(imageid, 
					siteID, 
					imageTypeID,
					sentence.toLowerCase(), 
					finding, 
					keywords, 
					neut, 
					pos, 
					neg, 
					section_of_sentence); // Runs sentence level analysis
		
			index = splitIntoSentences.current(); // update to next sentence
		}

	}


	// Method to find REGEX, NEGEX variables at the sentence level
	private void RegexSentenceLevel(String imageid, 
			String siteID, 
			String imageTypeID,
			String sentence, 
			String finding, 
			String[] keywords, 
			String[] neut, 
			String[] pos, 
			String[] neg,
			String section_of_sentence) throws Exception {
		String regex = "0"; // initialize
		String negex = "0"; // initialize
		String keyword_trigger = " "; // initialize

		ArrayList<String> negexMarkup = new ArrayList<String>();

		//ConText myContext = new ConText();
		FastContext fc = new FastContext(RuleBasedNLP.negationPatterns, true);
		
		for(String keyword : keywords){
			// find keywords
			Pattern p = Pattern.compile(keyword);
			Matcher m = p.matcher(sentence);
			
			while(m.find()){ // loop through all the found keywords
				regex = "1";
				negexMarkup = fc.processContext(sentence,m.start(), m.end(),15);
						
				keyword_trigger = keyword;
				if(negexMarkup.indexOf("negated") != -1) negex = "1"; // keyword is negated
	
				}
			}
		
		List<String> temp = Arrays.asList(imageid,
				siteID,
				imageTypeID,
				finding,
				sentence,
				section_of_sentence,
				regex,
				negex,
				keyword_trigger);
		
		RuleBasedNLP.errorArray.add(temp);
		
	}

}