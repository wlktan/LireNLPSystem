---
# LireNLPSystem package documentation
---

### Installation

To install, run the following code:
```{r installation}
install.packages("devtools")
library("devtools")
devtools::install_github("wlktan/LireNLPSystem")
library(LireNLPSystem)
```

For the demo, you should also have the following installed:
```{r}
install.packages("readr")
install.packages("dplyr")
install.packages("tidyr")
install.packages("caret")

library(LireNLPSystem)
library(readr)
library(dplyr)
library(tidyr)
library(caret)

```
### About

See project description here: https://docs.google.com/a/uw.edu/document/d/1O52W7TtHBVT8kDHx6PuBfVOKpJGo9FKp-T2bESbyrmM/edit?usp=sharing

More to come.

### Functions overview

There are five main R functions in this package:  

* SectionSegmentation
* RuleBasedNLP_JavaSentence
* RuleBasedNLP
* CreateTextFeatures
* MachineLearningNLP

These functions together creates the workflow for the NLP system for the LIRE project. The NLP system includes the 26 findings described in Tan et. all (Academic Radiology, 2018), as well as 10 additional ``rare and serious" findings.

#### SectionSegmentation

This function takes in a data frame, and segments the \textt{imagereporttext} column into the following sections:

* History
* Exam
* Comparison
* Technique
* Body
* Impression
* Datetime

Some of these sections may be empty. This code is developed and validated ONLY for the four LIRE sites:

* site = 1: Kaiser Permanente Washington (previously Group Health)
* site = 2: Kaiser Permanente Northen California
* site = 3: Henry Ford
* site = 4: Mayo Clinic

The default is \texttt{site = 2}. If you are NOT using LIRE reports, the algorithm may be inaccurate.

Example usage:
```{r section_segmentation}

### This is fake data.
text.df <- data.frame(patientID = c("W231", "W2242", "W452", "5235"),
                           examID = c("631182", "1226", "2090", "1939"),
                           siteID = c(2,2,2,2),
                           imageTypeID = c(1,3,1,3),
                           imagereporttext = c("** HISTORY **: Progressive radicular symptoms for 8 weeks Comparison study: None 
                                               ** FINDINGS **: Dextroconvex scoliosis with apex at L3. 
                                               ** IMPRESSION **: Scoliosis present.",
                                               "** FINDINGS **: Disk height loss is present focally. 
                                               ** IMPRESSION **:  Mild to moderate broad-based disc bulge.",
                                               "** FINDINGS **: canal stenosis and mild narrowing of the left latera. 
                                               ** IMPRESSION **: mild bilateral foraminal narrowing.",
                                               "** FINDINGS **: Disc unremarkable. 
                                               ** IMPRESSION **:  Foraminal stenosis."))


segmented.reports <- SectionSegmentation(text.df, site = 2)
View(segmented.reports)

```

#### RuleBasedNLP_JavaSentence

This function takes in a data frame, with at the minimum these columns:

* \texttt{imageid}: Unique identifier of report
* \texttt{bodyText}: Column of text of report body
* \texttt{impressionText}: Column of text of report impression
* \texttt{findings_longstring}: String of findings separated with \texttt{;}, for example \texttt{disc_degeneration;endplate_edema}. Full finding list available in the lire_finding_matrix.xlsx file on GitHub page.

This function assumes reports are sectioned into body and impression. To use this function if only one column of report text is available, please create an extra column for impression and fill it with \texttt{NA}. 

The function passes the data frame to the RuleBasedNLP.jar file in \texttt{inst/java}, and outputs sentence level prediction of the following:

* Sentence: The exact sentence from report.
* Section of sentence: Which section (body or impression) sentence is from.
* regex: If a related keyword to the finding is present.
* negex: If the keyword is negated.
* keyword: The regular expression that triggered the algorithm.

Note that negex can only be 1 if regex is 1.

Example usage:
```{r rb_java}
### Create unique identifier for each report: For LIRE data, it is patientID + examID
segmented.reports <- segmented.reports %>%
  dplyr::mutate(imageid = paste(patientID, examID, sep = "_"))

### This is the list of LIRE findings
finding.list <- c("spondylolisthesis",
                  "annular_fissure",
                  "disc_bulge",
                  "disc_degeneration",
                  "disc_desiccation",
                  "disc_height_loss",
                  "disc_protrusion",
                  "facet_degeneration")
                      
regex.df.java <- RuleBasedNLP_JavaSentence(segmented.reports,
                               imageid = "imageid",
                               bodyText = "body",
                               impressionText = "impression",
                               findings_longstring = paste(finding.list,collapse = ";")
                               )
```

#### RuleBasedNLP

This function takes in the sentence-by-sentence output of the \texttt{RuleBasedNLP_JavaSentence} function, and aggregates over all sentences to get a report level prediction. For every finding, the logic is as follows:

* Report level prediction: See lookup table below.
* Section level prediction:
  + 1, if at least one sentence with a non-negated keyword.
  + -1, if all keywords are negated.
  + 0, otherwise.
  
Example usage:
```{r rb}

rbnlp.tb <- data.frame(body = c(1,1,1,0,0,0,-1,-1,-1),
impression = c(1,0,-1,1,0,-1,1,0,-1),
rules_nlp = c(1,1,-1,1,0,-1,1,-1,-1))

View(rbnlp.tb)                       
regex.df.list <- RuleBasedNLP(regex.df.java) 

# This is the ``wide" data frame to be used in the machine-learning predictions
regex.df.wide <- regex.df.list$regex.df.wide

# This is the data frame of rules NLP prediction
rules.nlp.df <- regex.df.list$rules.nlp.df

```

#### CreateTextFeatures

This function takes a data frame with at the minimum these columns:

* \texttt{imageid}: Unique identifier of report
* \texttt{text.cols}: Columns of text of report body

It will create binary indicator of N-gram (unigrams, bigrams, trigrams) features separately for each column of text. However, to pre-process data so that it is compatible with the machine-learning feature weights, \texttt{text.cols} should be a vector of length 2 corresponding to the body and impression columns.

It will return a data frame based on the document-feature matrix (dfm) object: rows are reports and columns are features.

Example usage:
```{r create_text_features}
unigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("body","impression"),
                               n_gram_length = 1)
bigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("body","impression"),
                               n_gram_length = 2)
trigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("body","impression"),
                               n_gram_length = 3)

text.dfm <- unigrams %>%
  inner_join(bigrams, by = "imageid") %>%
  inner_join(trigrams, by = "imageid")

```

#### MachineLearningNLP

This function takes in features and outputs machine-learning NLP predictions. Three types of features are required:

* N-grams in section: This can be obtained using the \texttt{CreateTextFeatures} function.
* Report level regex and negex: This can be obtained using the \texttt{RuleBasedNLP} function (the .$regex.df.wide data frame).
* LIRE study site and imaging modality: These need to be formatted as indicator matrices (see example below).

At the minimum, report level regex/negex and N-grams in section are required. The algorithm will still run if study site and imaging modality information is not available, however the accuracy is unknown. Since this algorithm was developed specific for reports from LIRE study sites, please use it on other report types with awareness.

Example usage:
```{r ml}
### This is the same text.dfm in the demo.
### Need to make sure that the correct prefixes BODY and IMP are used!
colnames(text.dfm) <- gsub("IMPRESSION", "IMP", colnames(text.dfm))

#### This series of code transform numeric site and imageTypeID (modality) into indicator matrices
ftr <- segmented.reports %>% 
  dplyr::select(imageid, siteID, imageTypeID) %>%
  dplyr::rename(site = siteID,
                modality = imageTypeID) %>%
  dplyr::mutate(modality = ifelse(modality == 1, "XR", "MR")) %>%
  dplyr::mutate_all(as.factor)

site.and.modality <- predict(dummyVars(imageid ~ ., data = ftr), newdata = ftr) %>%
  as.data.frame() %>%
  dplyr::mutate(imageid = ftr$imageid)
colnames(site.and.modality) <- gsub("\\.", "_", colnames(site.and.modality))

text.dfm <- text.dfm %>%
  left_join(site.and.modality, by = "imageid")

### Apply machine-learning model tuned parameters
data(ml_feature_weights)

ml.nlp.df <- MachineLearningNLP(finding.list, 
                         text.dfm, 
                         regex.df.wide,
                         ml_feature_weights,
                         grouping_var = "imageid")
                            
### Combine rules and ML into single data.frame
nlp.df <- segmented.reports %>% dplyr::select(imageid, siteID, imageTypeID) %>%
  left_join(rules.nlp.df, by = "imageid") %>%
  left_join(ml.nlp.df, by = "imageid") %>%
  separate(imageid, into = c("patientID", "examID"), sep = "_")

View(nlp.df)
```


### News and updates
9/29/2017: Working on rewriting Java code for interface with R through rJava.

10/6/2017: 
* Completed:  
  + First end-to-end working pipeline. All the functions should work with the tiny example on this page. 
* Working on:  
  + Unit testing
  + Speed up data processing especially when passing between R/Java
  + Re-writing code for generalizability.

9/19/2017: 
* Easy loading of ML model coefficients

