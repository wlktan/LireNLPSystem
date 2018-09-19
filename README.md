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

You should also have the following installed:
```{r}
install.packages("rJava")
install.packages("dplyr")
install.packages("tidyr")
install.packages("quanteda")
install.packages("caret")
install.packages("xlsx")

```
### About

See project description here: https://docs.google.com/a/uw.edu/document/d/1O52W7TtHBVT8kDHx6PuBfVOKpJGo9FKp-T2bESbyrmM/edit?usp=sharing

WARNING: This package has not been fully tested; use at your own risk. If you try it out with examples outside of the tutorial, please also be mindful that this package has NOT been optimized for speed (highly recommend you chunk your data sets into smaller subsets - like 25 rows or so).

### Functions overview

There are five main R functions in this package:  

* SectionSegmentation
* RuleBasedNLP_JavaSentence
* RuleBasedNLP
* CreateTextFeatures
* MachineLearningNLP

which together defines a workflow/pipeline for getting NLP predictions of radiographic findings from radiology text reports.

#### SectionSegmentation
Takes in a data frame and outputs the sectioned report text.

Additional features to be developed:
* Allow user to specify the name of the column to be segmented into sections (right now it uses imagereporttext).
* Test edge cases to ensure regular expressions are splitting up sections correctly.
* Throws error messages if user enters incorrect input.

Example usage (these reports are fake data).
```{r section_segmentation}
library(LireNLPSystem)

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
Takes in segmented reports data frame and runs JAR file to obtain sentence-by-sentence rules-based NLP predictions.

Additional features to be developed:
* Additional text pre-processing

Example usage:
```{r rb_java}
### Create unique identifier for each report 
### For LIRE data, it is patientID + examID

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
                               bodyText = "preprocessed_findings",
                               impressionText = "preprocessed_impression",
                               findings_longstring = paste(finding.list,collapse = ";")
                               )
```

#### RuleBasedNLP
Takes in regex.df.java data frame and aggregates the regex and negex predictions for every report and finding, according to the following logic:  
* If there is at least one non-negated sentence in a section (body or impression), regex = 1 in the section.
* If there is conflict between body and impression, go with body.

Additional features to be developed:
* Additional text pre-processing

Example usage:

```{r rb}
regex.df.list <- RuleBasedNLP(regex.df.java) 

regex.df.wide <- regex.df.list$regex.df.wide
rules.nlp.df <- regex.df.list$rules.nlp.df

```

#### CreateTextFeatures

Takes as input the segmented reports data frame, and outputs a data frame with text-based predictors. 

Additional features to be developed:
* Test for features compatibility with developed models yet (e.g. fuzzy matching)

Example usage:
```{r create_text_features}
unigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("preprocessed_findings","preprocessed_impression"),
                               n_gram_length = 1)
bigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("preprocessed_findings","preprocessed_impression"),
                               n_gram_length = 2)
trigrams <- CreateTextFeatures(as.data.frame(segmented.reports),  
                               id_col = "imageid", 
                               text.cols = c("preprocessed_findings","preprocessed_impression"),
                               n_gram_length = 3)

text.dfm <- unigrams %>%
  inner_join(bigrams, by = "imageid") %>%
  inner_join(trigrams, by = "imageid")
colnames(text.dfm) <- gsub("PREPROCESSED_FINDINGS", "BODY", colnames(text.dfm))
colnames(text.dfm) <- gsub("PREPROCESSED_IMPRESSION", "IMP", colnames(text.dfm))
```

#### MachineLearningNLP

Takes as input the text-based predictors and regex/negex predictors, outputs Machine-Learning NLP predictions

Example usage:
```{r ml}

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

