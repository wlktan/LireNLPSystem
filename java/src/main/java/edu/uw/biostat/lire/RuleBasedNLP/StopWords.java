package edu.uw.biostat.lire.RuleBasedNLP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

public class StopWords {
	
	public StopWords (){
		
	}
	
	public String[] InitializeStopwords (){

		 Scanner infile = null;
		try {
			infile = new Scanner(new File("C:/Users/wlktan/Desktop/lirenlp/dictionary/stopwords2.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("No file found.. #sadface");
			e.printStackTrace();
		}
		 
		List<String> tokens = new ArrayList<String>();
		while (infile.hasNext()) {
		    tokens.add(infile.nextLine());
		}
		String[] fileStopWords = tokens.toArray(new String[0]);
		
		String[] manualStopWords = {	
			"a","ag" ,"am","an","ar", "abd","and","adn","ain","ala","alk","ani","asm",
			"ajnr","andy","asid","asvd",
			"andy","also,", "andrew","anupam","ap","are", "as", "at","addendum",
			"b", "bb","be", "bil","ble","bun","but","br" ,"by","because",
			"c","ca","can","cc","cf","car","ccc","chr","cll","cip","cor","csf","cuf","cxr","cabg","cava",
			"christopher","colleen","comment",
			"d","dai","david","dictate", "dm","do","dr","doe","dictationaddendum","due","explain",
			"e","edg","emg","end","esi","esr","elli","epic","erik","eros","eckstein","ends","end",
			"f","fs","for","fad","fcd","fhd","flx", "fod", "fpd","fri","folz",
			"g","ga","gd","george", "gp","griffith","gad","gfr",
			"h","have","has","hi","how","her","him",
			"i","ii","ij","if", "in", "into", "is", "it","isn","iso","ivc",
			"j","jmp","jng","jrh","jvu","james","jean","jose",		
			"k","kristin","kei",
			"l","le","lee","li","ls","lt","ly","lai","lao","lhe","lle","lmb","lss",
			"m","md","m.d", "me","mo","mcc","mch","mgu","mjn","moi","msk","mva","mvc",
			"n","na","nd","nki",
			"o","oa","of", "on", "or","os","our",
			"p","pa","po","pho","pon","plif",
			"q","qqq",
			"r", "richard","robert","rai","rao","rle","ruq","ryan",
			"s","sa","se","si","so","st","sx","she", "software","such","sen","spt",
			"t","tl","that", "the", "their", "then", "there", "these","they", "this", "to", "thu",
			"trr",
			"u","us","uy",
			"v","vw","via",
			"w","was","we", "what","when","which","who","why","will", "with","would","wu","wai","wmf",
			"x","x1",
			"y","you",
			"z"
			};
		
		String[] allStopWords = (String[])ArrayUtils.addAll(fileStopWords, manualStopWords);
		
		return allStopWords;
	}

}
