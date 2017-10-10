---
# LireNLPSystem package documentation
---

### Installation

To install, run the following code:
```{r installation}
install.packages("devtools")
library("devtools")
devtools::install_github("wlktan/LireNlpSystem")
library(LireNlpSystem)
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
* GetRegexNegex
* AggregateRegex
* CreateTextFeatures
* ApplyNLPModels

which together defines a workflow/pipeline for getting NLP predictions of radiographic findings from radiology text reports.

#### SectionSegmentation
Takes in a data frame and outputs the sectioned report text.

Additional features to be developed:
* Allow user to specify the name of the column to be segmented into sections (right now it uses imagereporttext).
* Test edge cases to ensure regular expressions are splitting up sections correctly.
* Throws error messages if user enters incorrect input.

Example usage (these reports are fake data).
```{r section_segmentation}
library(LireNlpSystem)

annotated.df <- data.frame(patientID = c("W231", "W2242", "W452", "5235"),
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


segmented.reports <- SectionSegmentation(annotated.df, site = 2)
View(segmented.reports)

```

#### GetRegexNegex
Takes in segmented reports data frame and runs JAR file to obtain sentence-by-sentence rules-based NLP predictions.

Additional features to be developed:
* Additional text pre-processing

Example usage:
```{r get_regex_negex}
regex.df.java <- GetRegexNegex(segmented.reports,
                               imageid = "patientID",
                               siteID = "siteID",
                               imageTypeID = "imageTypeID",
                               bodyText = "body",
                               impressionText = "impression")
```

#### AggregateRegex
Takes in regex.df.java data frame and aggregates the regex and negex predictions for every report and finding, according to the following logic:  
* If there is at least one non-negated sentence in a section (body or impression), regex = 1 in the section.
* If there is conflict between body and impression, go with body.

Additional features to be developed:
* Additional text pre-processing

Example usage:

```{r aggregate_regex}
regex.df <- AggregateRegex(regex.df.java) 

```

#### CreateTextFeatures

Takes as input the segmented reports data frame, and outputs a data frame with text-based predictors. 

Additional features to be developed:
* Test for features compatibility with developed models yet (e.g. fuzzy matching)

Example usage:
```{r create_text_features}
text.dfm <- CreateTextFeatures(segmented.reports,  
                     id_col = "patientID", 
                     text.cols = c("body", "impression"),
                     all.stop.words = setdiff(stopwords(), c("no", "not", "nor")),
                     finding.dictionary = NULL,
                     min_count = 1,
                     min_doc = 1,
                     n_gram_length = 3)
```

#### ApplyNLPModels

Takes as input the text-based predictors and regex/negex predictors, outputs NLP predictions (rules and machine-learning).

Additional features to be developed:
* Fix bug to read in model_coef.xlsx file from the R package properly, or alternative ways to load data.


Example usage:
```{r apply_nlp_models}

finding.list <- c("Annular Fissure", "Disc Bulge", "Facet Degeneration")
nlp.preds <- ApplyNLPModels(finding.list, 
                            text.dfm, 
                            regex.df)
                            
View(nlp.preds)
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

