package edu.uw.biostat.lire.RuleBasedNLP;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;

public class KeywordDictionary implements Map {

	public static Map<String, String[]> FindingListKeywords = new HashMap<String, String[]>();

	// base constructor
	public KeywordDictionary(){

	}

	Map<String, String[]> initialize(){
		// ################ START RARE & SERIOUS FINDINGS #############################
	/*	String[] AorticAneurysm = {"aaa", "AAA",
				"abdominal\\s*aortic\\s*aneurysm",
				"aortic\\s*aneurysm","aneurysm(\\s*\\w*){1,4}aorta",
				"aortic\\s*aneurys\\w*\\s*enlargement","enlargement(\\s*\\w*){1,4}aorta",
				"aortic\\s*dilatation","dilatation(\\s*\\w*){1,4}aorta",
				"aortic\\s*dilation", "dilation(\\s*\\w*){1,4}aorta",
				"borderline\\s*aaa",
				"ectasia"};
		
		String[] CordCompression = {
				"(cord|conus|syrinx)\\s*compression", "compression(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*damage", "damage(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*effacem", "effac\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*imping", "imping\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*indent", "indent\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*injury", "injury\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*deform", "deform\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*obliteration", "obliteration\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*flat", "flat\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*indentation", "indent\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)"};
	
		String[] CaudaEquinaCompression = {
				"cauda\\s*equina\\s*compress","compression(\\s*\\w*){1,4}(cauda\\s*equina)",
				"cauda\\s*equina\\s*compromise","compromise(\\s*\\w*){1,4}(cauda\\s*equina)",
				"cauda\\s*equina\\s*syndrome"
				};
	
		String[] CordSignalAbnormality = {
				"(cord|conus|syrinx)\\s*abnormal\\s*signal", "abnormal\\s*signal(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*high\\s*signal", "high\\s*signal(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*high\\s*t.?(1|2)", "high\\s*t.?(1|2)(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*hyperintensity", "hyperintensity(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*increased\\s*intensity", "increased\\s*intensity(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*low\\s*t.?(1|2)", "low\\s*t.?(1|2)(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*signal\\s*loss", "signal\\s*loss(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*decreas\\w*\\s*t.?(1|2)", "decreas\\w*\\s*t.?(1|2)(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*flat", "flat\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)",
				"(cord|conus|syrinx)\\s*indentation", "indent\\w*(\\s*\\w*){1,4}(cord|conus|syrinx)"};*/
		
		String[] Fracture = {"callous\\s*form",
				"fracture", 
				"stress\\s*reaction",
				"subluxation",
				"wedg",
				"convexity",
				"variability\\s*(in)?\\s*morphology",
				"variability\\s*(in)\\s*size",
				"deformity",
				"scallop",
				"depression", 
		"loss\\s*(of)?\\s*end\\s*plate\\s*vertebral\\s*body"};
		
		// Specificity of location??
		/*String[] Infection = {"abscess",
				"arachnoiditis",
				"discitis",
				"diskitis",
				"infectio",
				"osteomyelitis",
				"phlegmon",
				"fluid\\s*collection"};

		String[] SpinalMalignancy = {
				"(bone|cord|spin|vertebra)\\s*cancer", "cancer(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*cyst", "cyst(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*mass", "mass(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*(destruct\\w*\\s*bony\\s*lesion)", "(destruct\\w*\\s*bony\\s*lesion)(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"destructive\\s*process\\s*not\\s*excluded",
				"diffuse\\s*marrow\\s*replacing\\s*process",
				"(bone|cord|spin|vertebra)\\s*(lytic\\s*lesion)", "(lytic\\s*lesion)(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*(malignan)", "(malignan)(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*metastasis", "metastasis(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*metastasize", "metastasize(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*metastases", "metastases(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*mets\\s", "mets\\s(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*moth.?eaten", "moth.?eaten(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*neoplasm", "neoplasm(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*(pathologic\\s*fracture)", "(pathologic\\s*fracture)(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"(bone|cord|spin|vertebra)\\s*plasmacytoma", "plasmacytoma(\\s*\\w*){1,4}(bone|cord|spin|vertebra)",
				"questionable\\s*well\\s*demarcated\\s*lucent\\s*area", 
				"(bone|cord|spin|vertebra)\\s*tumor", "tumor(\\s*\\w*){1,4}(bone|cord|spin|vertebra)"
				
		};

		//(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)

		String[] ExtraSpinalMalignancy = {
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*cancer", "cancer(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*cyst", "cyst(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*mass", "mass(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*(destruct\\w*\\s*bony\\s*lesion)", "(destruct\\w*\\s*bony\\s*lesion)(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"destructive\\s*process\\s*not\\s*excluded",
				"diffuse\\s*marrow\\s*replacing\\s*process",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*(lytic\\s*lesion)", "(lytic\\s*lesion)(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*(malignan)", "(malignan)(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*metastasis", "metastasis(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*metastasize", "metastasize(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*metastases", "metastases(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*mets\\s", "mets\\s(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*moth.?eaten", "moth.?eaten(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*neoplasm", "neoplasm(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*(pathologic\\s*fracture)", "(pathologic\\s*fracture)(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*plasmacytoma", "plasmacytoma(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)",
				"questionable\\s*well\\s*demarcated\\s*lucent\\s*area", 
				"(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)\\s*tumor", "tumor(\\s*\\w*){1,4}(adrenal|gastrointestinal|gi|kidney|renal|liver|hepatic|pancreatic|prostate|pulmonary|renal|necrotic|lymphadenopathy)"
				
		};
		

		
		String[] Spondyloarthropathy = {"ankylosing\\s*spondylitis",
				"ankylosis(\\s*\\w*){1,4}sacroiliac\\s*joints",
				"arthropathy(\\s*\\w*){1,4}(inflammatory\\s*bowel\\s*disease|ibd)",
				"reactive\\s*arthritis",
				"reiter.?s\\s*syndrome",
				"sacroiliitis",
				"spondyloarthropathy",
				"spondyloarthritis"};
		
		String[] PositiveAlert = {"flagged\\s*as\\s*abnormal",
				"annotated\\s*as\\s*abnormal",
				"designated\\s*as\\s*abnormal",
				"POSITIVE\\s*ALERT",
				"positive\\s*alert"};
		
		FindingListKeywords.put("aortic_aneurysm", AorticAneurysm);
		FindingListKeywords.put("cord_compression", CordCompression);
		FindingListKeywords.put("cauda_equina_compression", CaudaEquinaCompression);
		FindingListKeywords.put("cord_signal_abnormality", CordSignalAbnormality);
		FindingListKeywords.put("fracture", Fracture);
		FindingListKeywords.put("infection", Infection);
		FindingListKeywords.put("spinal_malignancy", SpinalMalignancy);
		FindingListKeywords.put("extra_spinal_malignancy", ExtraSpinalMalignancy);
		FindingListKeywords.put("spondyloarthropathy", Spondyloarthropathy);
		FindingListKeywords.put("positive_alert", PositiveAlert);*/
		
		
		// ################ END RARE & SERIOUS FINDINGS #############################
		
		
		String[] CentralStenosis = {"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*stenos(i|e)s", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*attenuat", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*borderline\\*size",
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*bony\\s*compromise", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*broad\\s*component", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*compres", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*deflect",
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*effac", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*flatten", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*impingement", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*indentation", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*narrow", 
				"small\\s*diameter\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)", 
				"crowding\\s*(of)?\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",
				"components\\s*(to)?\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*compromise", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*encroachment",
				"extend\\s*(into)?\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",
				"fills\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",
				"into\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)", 
				"(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)\\s*involved", 
				"large(ly)?\\s*occupies\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)", 
				"protrusion\\s*(into)?\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",
				"within\\s*(central|canal|congenital|trefoil|the\\s*cal\\s*sac|arachnoid|(3|three)\\s*side)",	
		"loss of epidural fat"};

		String[] LateralRecessStenosis = {
				"attenuate(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}attenuate",
				"borderline\\s*size(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}borderline\\s*size",
				"bony\\s*\\w*compromi(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}bony\\s*\\w*compromi",
				"broad\\s*component(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}broad\\s*component",
				"(?<!non)compres(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}(?<!non)compres\\w*",
				"deflect(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}deflect",
				"efface(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}efface",
				"flatten(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}flatten",
				"imping(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}imping",
				"indentation(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}indentation",
				"(?<!non)narrow(\\w*\\s*){1,5}(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}(?<!non)narrow",
				"retropulsion(\\w*\\s*){1,5}(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}retropulsion",
				"small(\\w*\\s*){1,4}diameter(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,5}small(\\w*\\s*){1,4}diameter",
				"(paracentral|(?<!bi)latera\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)(\\w*\\s*){1,4}stenos\\w*",
				"stenos(\\w*\\s*){1,4}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",

				// Intrusion synonyms
				"along(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"crowding(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"components\\s*to(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"encroach(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"extending(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"fills(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"into(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"within(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"largely\\s*occupies(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"truncation(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)",
				"dis(c|k)\\s*protru(\\w*\\s*){1,5}(paracentral|(?<!bi)lateral\\s*recc?es|(?<!bi)lateral\\s*the\\s*cal\\s*sac|subarticular)"
		};

		String[] ForaminalStenosis = {
				"attenuate(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}attenuate",
				"borderline\\s*size(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}borderline\\s*size",
				"bony\\s*\\w*compromi(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}bony\\s*\\w*compromi",
				"broad\\s*component(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}broad\\s*component",
				"(?<!non)compres(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}(?<!non)compres\\w*",
				"deflect(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}deflect",
				"efface(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}efface",
				"flatten(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}flatten",
				"imping(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}imping",
				"indentation(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}indentation",
				"(?<!non)narrow(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}(?<!non)narrow",
				"retropulsion(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}retropulsion",
				"small(\\w*\\s*){1,4}diameter(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,5}small(\\w*\\s*){1,4}diameter",
				"(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)(\\w*\\s*){1,4}stenos\\w*",
				"stenos(\\w*\\s*){1,4}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",

				// Intrusion synonyms
				"along(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"crowding(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"components\\s*to(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"encroach(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"extending(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"fills(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"into(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"largely\\s*occupies(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
				"truncation(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)",
		"dis(c|k)\\s*protru(\\w*\\s*){1,5}(foram(a|e|i)|neur(a|o)l?\\s*foram(a|e|i)n)"};

		String[] AnyStenosis = {"spinal\\s*stenos","stenos(e|i)s"};
		AnyStenosis = ArrayUtils.addAll(AnyStenosis, CentralStenosis);
		AnyStenosis = ArrayUtils.addAll(AnyStenosis, ForaminalStenosis);
		AnyStenosis = ArrayUtils.addAll(AnyStenosis, LateralRecessStenosis);


		String[] EndplateEdema = {		
				// endplate <-> edema
				"end\\s*plate(\\w*\\s*){1,3}edema",
				"edema(\\w*\\s*-*\\*/*){1,10}end\\s*plate",

				// modic <-> type1
				"type.?(1|i|one)(?!i)(\\w*\\s*){1,3}m(o|e)dic",
				"m(o|e)dic(\\w*\\s*){1,3}type.?(1|i|one)(?!i)(-(2|ii|two))?",

				// acute phase degenerative signal change
				"acute\\s*phase(\\w*\\s*){1,5}change",
				"acute\\s*degenerative\\s*end\\s*plate\\s*change",

				// high/increased signal/stir/t2 <-> endplate
				"(high|increased)\\s*signal(\\w*\\s*-*\\*/*){1,10}end\\s*plate",
				"end\\s*plate(\\w*\\s*){1,3}(high|increased)\\s*signal",
				"(high|increased)\\s*stir(\\w*\\s*-*\\*/*){1,10}end\\s*plate",
				"end\\s*plate(\\w*\\s*){1,3}(high|increased)\\s*stir",
				"(high|increased)\\s*t2(\\w*\\s*-*\\*/*){1,10}end\\s*plate",
				"end\\s*plate(\\w*\\s*){1,3}(high|increased)\\s*t2",
				"t2\\s*hyperintensity(\\w*\\s*-*\\*/*){1,10}end\\s*plate",
				"end\\s*plate(\\w*\\s*){1,3}t2\\s*hyperintensity",
				"type.?(1|i|one)(?!i)(\\w*\\s*-*\\*/*){1,10}end\\s*plate",
		};

		String[] Listhesis1 = {"grade\\s*(1|i|one)\\s(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}grade\\s*(1|i|one)\\s",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}first\\s*(degree|grade)",
				"first\\s*grade(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"first\\s*degree(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"mild(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"min(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"minimal(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"slight(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"trace(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"less\\s*(than)?\\s*25(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"<25(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)"
		};

		String[] Listhesis2 = {"grade\\s*((1|i|one)-\\s*)?(2|ii|two)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"second\\s*grade(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"second\\s*degree(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"25-50(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}grade\\s*(2|ii|two)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}second\\s*(degree|grade)",

				// grade 2
				"grade\\s*(3|iii|three)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"third\\s*grade(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"third\\s*degree(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"50-75(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}grade\\s*(3|ii|three)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}third\\s*(degree|grade)",

				// grade 4
				"grade\\s*(4|iv|four)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"fo(u)?rth\\s*grade(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"fo(u)?rth\\s*degree(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"75-100(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				">75(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"greater\\s*(than)?\\s*75(%|percent)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}grade\\s*(4|iv|four)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}fou?rth\\s*(degree|grade)",

				// grade 5
				"grade\\s*(5|v|five)(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"fifth\\s*grade(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"fifth\\s*degree(\\w*\\s*){1,3}(listhes(i|e)s|slip|subluxation|retropulsion)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}grade\\s*(5|v|five)",
				"(listhes(i|e)s|slip|subluxation|retropulsion)(\\w*\\s*){1,3}fifth\\s*(degree|grade)",

		};

		String[] Spondylolisthesis = {"spondylolisthes","listhes"};
		Spondylolisthesis  = ArrayUtils.addAll(Spondylolisthesis , Listhesis1);
		Spondylolisthesis  = ArrayUtils.addAll(Spondylolisthesis , Listhesis2);

		String[] DiscBulge = {"bulg\\w*",
				"dis(\\w*\\s*){1,4}compl\\w*", 
				"osteop\\w*\\s*compl\\w*",
				"dis(c|k)(\\w*\\s*){1,4}spur",
				"dis(c|k)(\\w*\\s*){1,4}ridge",
		"spur\\w*\\s*dis(c|k)"};

		String[] DiscProtrusion = {"dis(s|k)\\s*protrusion",
		"dis(c|k)\\s*protrusion"};

		String[] DiscExtrusion = {"dis(c|k)\\s*extru", 
				"focal\\s*extru",
				"extru(\\w*\\s*)dis(c|k)",
				"extru(\\w*\\s*)focal",
				"sequest\\w*",
		"free\\s*fragm\\w*"};


		String[] DiscDesiccation = {"dess?icc?ation",
				"dis(c|k)(\\w*\\s*){1,4}dehydrat\\w*",
				"dehydra(\\w*\\s*){1,4}dis(c|k)",
				"decreas(\\w*\\s*){1,4}sign\\w*",
				"signal(\\w*\\s*){1,4}ldecreas\\w*",
				"los(\\w*\\s*){1,6}signal",
		"signal(\\w*\\s*){1,4}los\\w*"};


		String[] DiscDegeneration = {
				// Calficication
				"dis(c|k)\\s*calcifi\\w*",
				"calcifi(\\w*\\s*){1,7}dis(c|k)",

				// ossification
				"dis(c|k)\\s*ossifi\\w*",
				"ossifi(\\w*\\s*){1,7}dis",

				// vacuum disc phenomena
				"vacuum\\s*dis(c|k)",
				"vacuum\\s*phenomen",

				// discogeneic changes
				"discogenic\\s*change",

				// disc degeneration
				"dis(c|k)\\s*degeneration",
				"degenerat(\\w*\\s*){0,7}dis",

				// degenerative disc disease
				"degenerat\\w*\\s*dis(c|k)\\s*diseas",
				"dis(c|k)\\s*degen\\w*\\s*chang",
				"ddd",

				// disc/adjacent level/fused level disease
				"degenerat\\w*\\s*dis(c|k)\\s*disease",
				"dis(c|k)\\s*(space\\s)?disease",
				"adjacent\\s*level\\s*disease",
				"fused\\s*level\\s*disease",

				// degenerative spaces
				"degenerat(\\w*\\s*){1,4}space",
				"degenerat(\\w*\\s*){1,4}interspace",
				"degenerat(\\w*\\s*){1,4}intervertebr",

				// disc loss
		"dis(c|k)\\s*loss"};


		String[] DiscHeightLoss= {
				"decreas(\\w*\\s*){1,4}heigh\\w*",
				"heigh(\\w*\\s*){1,4}decreas\\w*",
				"heigh(\\w*\\s*){1,2}los\\w*",
				"los(\\w*\\s*){1,3}(dis(c|k))?\\s*heigh\\w*",
				"spac(\\w*\\s*){1,2}los\\w*",
				"los(\\w*\\s*){1,3}spac\\w*",
				"dis(c|k)(\\w*\\s*){1,2}los\\w*",
				"los(\\w*\\s*){1,3}dis(c|k)",
				"(dis(c|k)|spac){1,2}narro\\w*",
				"narro(\\w*\\s*){1,3}(dis(c|k)|spac)\\w*",
				"(dis(c|k)|spac)(\\w*\\s*){1,2}collaps\\w*",
				"collaps(\\w*\\s*){1,3}(dis(c|k)|spac)\\w*",
				"flatten(\\w*\\s*){1,3}(dis(c|k)|vertebr)\\w*",
				"(dis(c|k)|vertebr)(\\w*\\s*){1,2}flatten\\w*",

				// direct words
				"dis(c|k)\\s*space\\s*narrow",
				"dis(c|k)\\s*height\\s*loss",
				"height\\s*loss",
				"interspace\\s*narrow",
				"loss\\s*of\\s*dis(c|k)\\s*height",
				"dis(c|k)\\s*height\\s*loss",

				// Spelling error/variation
		"dispace\\w*\\s*(narrow|los)\\w*"};


		String[] FacetDegeneration = {"(facet|joint|posterior\\s*element)(\\w*\\s*){1,4}degeneration",
				"(facet|joint|posterior\\s*element)\\s*arthropath", 
				"(facet|joint|posterior\\s*element)\\s*arthrosis",
				"(facet|joint|posterior\\s*element)\\s*change",
				"(facet|joint|posterior\\s*element)\\s*cyst", 
				"(facet|joint|posterior\\s*element)\\s*hypertrophy", 
				"(facet|joint|posterior\\s*element)\\s*narrow", 
				"(facet|joint|posterior\\s*element)\\s*osteoarthropathy",
				"prominent\\s*(facet|joint|posterior\\s*element)",
				"(facet|joint|posterior\\s*element)\\s*sclero\\w*", 
				"(facet|joint|posterior\\s*element)\\s*spondylosis",
				"(facet|joint|posterior\\s*element)\\s*spondylotic",
				"(facet|joint|posterior\\s*element)\\s*spur",
				"(facet|joint|posterior\\s*element)\\s*fluid", 
				"(facet|joint|posterior\\s*element)\\s*osteophytes",
		"(facet|joint|posterior\\s*element)\\s*effusion"};


		String[] AnnularFissure = {"ann?ul\\w+\\s*fissure",
				"ann?ul\\w+\\s*tear",
				"edema(\\w*\\s*){1,5}ann?ulu\\w+",
				"high\\s*(signal)?\\s*intens\\w*\\s*zone",
		"hiz"};


		String[] NerveRootContact = {
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*contact",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*affect", 
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*association",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*at\\s*origin", 
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*encasement", 
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*exten\\w*", 
				"surrounding(\\w*\\s*){1,10}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"abut(\\w*\\s*){1,10}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)", 
				"contact(\\w*\\s*){1,10}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)", 
				"crowding(\\w*\\s*){1,10}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"touching(\\w*\\s*){1,10}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)\\s*fat",

				// Other terminology
				"los(\\w*\\s*){1,5}perineur\\w*",
		"efface(\\w*\\s*){1,5}perineur\\w*"};


		String[] NerveRootDisplacedCompressed = {
				"ab\\w?ut(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}ab\\w?ut",
				"compres(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}compres",
				"compromise(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}compromise",
				"crush(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}crush",
				"depres(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}depres",
				"displac(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}displac",
				"distort(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}distort",
				"efface(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)", 
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}efface",
				"encroach(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}encroach",
				"flat(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}flat",
				"impressing(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}impressed",
				"indentation(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}indentation",
				"impingement(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}impingement",
				"mass\\s*effect(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}mass\\s*effect",
				"narrow(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}narrow",
				"largely\\s*occupies(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}largely\\s*occupied",
				"thickening(\\w*\\s*){1,5}(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)",
				"(root|nerv\\w*\\s*r(oo)?t|neur\\w*\\s*r(oo)?t|nerv\\w*\\s*element|neur\\w*\\s*r(oo)?t|neura\\w*\\s*foram(a|e|i)n)(\\w*\\s*){1,5}thicken\\w*"
		};

		// Get clarity on final finding list
		FindingListKeywords.put("Any Stenosis", AnyStenosis); // Central, LateralRec, Foraminal, or NOS // DOC
		FindingListKeywords.put("Central Stenosis", CentralStenosis); // DOC
		FindingListKeywords.put("Lateral Recess Stenosis", LateralRecessStenosis); // DOC
		FindingListKeywords.put("Foraminal Stenosis", ForaminalStenosis); // DOC
		FindingListKeywords.put("Endplate Edema Type 1 Modic", EndplateEdema); // DOC
		FindingListKeywords.put("Listhesis Grade 1", Listhesis1); // DOC
		FindingListKeywords.put("Listhesis Grade 2", Listhesis2); // DOC
		FindingListKeywords.put("Spondylolisthesis", Spondylolisthesis); // Grade 1, 2, NOS.. // DOC
		FindingListKeywords.put("Disc Bulge", DiscBulge); // DOC
		FindingListKeywords.put("Disc Protrusion", DiscProtrusion); // DOC
		FindingListKeywords.put("Disc Extrusion", DiscExtrusion); // DOC
		FindingListKeywords.put("Disc Desiccation", DiscDesiccation); // DOC
		FindingListKeywords.put("Disc Degeneration", DiscDegeneration); // DOC
		FindingListKeywords.put("Disc Height Loss", DiscHeightLoss); // DOC
		FindingListKeywords.put("Facet Degeneration", FacetDegeneration); // DOC
		FindingListKeywords.put("Annular Fissure", AnnularFissure); // DOC
		FindingListKeywords.put("Nerve Root Contact", NerveRootContact); // DOC
		FindingListKeywords.put("Nerve Root Displaced Compressed", NerveRootDisplacedCompressed); // DOC


		String[] Hemangioma = {"hemangioma","hemangiomas", "venous\\s*malformation"};

		String[] OsteophyteAnteriorColumn = {

				// Anterior spur/osteophyte/syndesmpohyte/hypertrophic changes
				"anterio(\\w*\\s*){1,5}spur",
				"anterio(\\w*\\s*){1,5}osteophyt",
				"anterio(\\w*\\s*){1,5}syndesmpohyt",
				"anterio(\\w*\\s*){1,5}osteophyt",

				// Lateral
				"latera(\\w*\\s*){1,3}spur",
				"latera(\\w*\\s*){1,3}osteophyt",
				"latera(\\w*\\s*){1,3}syndesmpohyt",

				// Marginal
				"margina(\\w*\\s*){1,3}spur",
				"margina(\\w*\\s*){1,3}osteophyt",
				"margina(\\w*\\s*){1,3}syndesmpohyt",

				// Peripheral
				"periphera(\\w*\\s*){1,3}spur",
				"peripher(\\w*\\s*){1,3}osteophyt",
				"peripher(\\w*\\s*){1,3}syndesmpohyt",

				// Ventral
				"ventra(\\w*\\s*){1,3}osteophyt",
				"ventra(\\w*\\s*){1,3}spur",

				// Posterior
				"posterio(\\w*\\s*){1,3}spur",
				"posterio(\\w*\\s*){1,3}osteophyt",

				// Endplate
				"end\\s*plat\\w*\\s*hypertrop(\\w*\\s*){1,2}chan",
				"end\\s*plat\\w*\\s*hypertrop(\\w*\\s*){1,2}spur",
				"end\\s*plat(\\w*\\s*){1,2}spur",
				"end\\s*plat(\\w*\\s*){1,2}osteophyt",

				// complexes
				"dis(c|k)\\s*osteophyt\\w*\\s*complex",
				"dis(c|k)\\s*spur\\s*complex",
				"dis(c|k)\\s*ridge\\s*complex",
				"dis(c|k)\\s*complex",
				"osteophyt\\w*\\s*complex",
				"dis(c|k)\\s*spur",
				"dis(c|k)\\s*and\\s*end\\s*plate\\s*osteophyt",

				// Other terminology
				"dish",
				"apophyseal\\s*spondylos(i|e)s",
		"spondylotic\\s*stenos(i|e)s"};

		String[] Spondylosis = {"spondylosis", "spondylitic", "spondylotic","spondylytic"};

		String[] DiscHerniation = {"herniat\\w*",
				"dis(c|k)\\s*prolaps",
		"extensi(\\w*\\s*){1,3}dis(c|k)\\s*material"};

		String[] Spondylolysis = {"spondylolysis",
				"pars\\s*defect", 
				"pars\\s*fractur",
		"isthmic\\s*spondylolisthesis"};
		
		String[] Scoliosis = {"scoliosis", "levoscoliosis", "curvature", "scoliotic",
		"spine\\s*tilts"};

		String[] AnyOsteophyte = {"osteophyte","syndesmpohyte"};
		AnyOsteophyte = ArrayUtils.addAll(AnyOsteophyte, OsteophyteAnteriorColumn);
		
		String[] AnyDegeneration = {"degeneration"};
		AnyDegeneration = ArrayUtils.addAll(AnyDegeneration,DiscDegeneration);
		AnyDegeneration = ArrayUtils.addAll(AnyDegeneration,FacetDegeneration);
		
		//FindingListKeywords.put("Hemangioma", Hemangioma);
		FindingListKeywords.put("Osteophyte anterior column", OsteophyteAnteriorColumn);
		FindingListKeywords.put("Spondylosis", Spondylosis); // Jerry email
		FindingListKeywords.put("Disc Herniation", DiscHerniation); // Jerry email
		FindingListKeywords.put("Fracture", Fracture);
		FindingListKeywords.put("Spondylolysis", Spondylolysis);
		FindingListKeywords.put("Scoliosis", Scoliosis); // Jerry email
		FindingListKeywords.put("Any Osteophyte", AnyOsteophyte);
		FindingListKeywords.put("Any Degeneration", AnyDegeneration); // Jerry email

		/*String[] CordCompression = {"cord\\s*(compress|impinge|damage|myelomalacia)",
				"conus\\s*(compress|impinge|damage|myelomalacia)",
		"cauda\\s*equina\\s*syndrom",
		"myelopathy",
		"spinal\\s*cord\\s*injury"};
		FindingListKeywords.put("Cord.Compression", CordCompression);*/

		return FindingListKeywords;

	}

	private static Map<String, String[]> put(String newFinding, String[]newKeywords){
		FindingListKeywords.put(newFinding, newKeywords);
		return FindingListKeywords;
	}

	private static Map<String, String[]> deleteFinding(String newFinding){
		FindingListKeywords.remove(newFinding);
		return FindingListKeywords;
	}

	// Public methods so other classes can access
	public String[] get(String finding) {
		String[] keywords = FindingListKeywords.get(finding);
		return keywords;
	}

	public int size() {
		int size =  FindingListKeywords.size();
		return size;
	}

	public void clear() {
		FindingListKeywords.clear();
	}

	public boolean containsKey(Object key) {
		boolean hasKey =  FindingListKeywords.containsKey(key);
		return hasKey;
	}

	public boolean containsValue(Object value) {
		boolean hasValue =  FindingListKeywords.containsValue(value);
		return hasValue;
	}

	public Object get(Object key) {
		Object obj =  FindingListKeywords.get(key);
		return obj;
	}

	public boolean isEmpty() {
		boolean isEmpty =  FindingListKeywords.isEmpty();
		return isEmpty;
	}

	public Set keySet() {
		Set set = FindingListKeywords.keySet();
		return set;
	}

	public Object put(Object key, Object value) {
		//Object obj = FindingListKeywords.put(key, value);
		//return obj;
		return null;
	}

	public void putAll(Map m) {
		FindingListKeywords.putAll(m);
	}

	public Object remove(Object key) {
		Object obj = FindingListKeywords.remove(key);
		return obj;
	}

	public Collection values() {
		Collection val = FindingListKeywords.values();
		return val;
	}

	public Set entrySet() {
		Set entrySet = FindingListKeywords.entrySet();
		return entrySet;
	}

}

