---
title: LireNLPSystem package documentation
output:
  pdf_document:
    toc: yes
    toc_depth: '2'
  html_document:
    df_print: paged
    fig_height: 6
    fig_width: 8
    toc: yes
    toc_depth: 2
---


# Overview

See project description here: https://docs.google.com/a/uw.edu/document/d/1O52W7TtHBVT8kDHx6PuBfVOKpJGo9FKp-T2bESbyrmM/edit?usp=sharing

# R functions summary/progress

There are five main R functions in this package:  

* SectionSegmentation
* GetRegexNegex
* AggregateRegex
* CreateTextFeatures
* ApplyNLPModels

## SectionSegmentation
Takes in a data frame and outputs the sectioned report text.

Additional features to be developed:
* Allow user to specify the name of the column to be segmented into sections (right now it uses imagereporttext).
* Test edge cases to ensure regular expressions are splitting up sections correctly.
* Throws error messages if user enters incorrect input.

Example usage:
```{r section_segmentation}
annotated.df <- data.frame(imageid = c("W231", "W2242", "W45", "5235"),
                           siteID = c(1,2,3,4),
                           imageTypeID = c(1,3,1,3),
                           imagereporttext = c("** HISTORY **: Progressive radicular symptoms for 8 weeks Comparison study: None ** FINDINGS **: Dextroconvex scoliosis with apex at L3. ",
                                               "** FINDINGS **: Disk height loss is present focally. ** IMPRESSION **:  Mild/moderate broad-based disc bulge.",
                                               "** FINDINGS **: canal stenosis and mild narrowing of the left latera. ** IMPRESSION **: mild bilateral foraminal narrowing.",
                                               "** FINDINGS **: Disc unremarkable. ** IMPRESSION **:  Scoliosis."))


segmented.reports <- bind_rows(SectionSegmentation(annotated.df, site = 1),
                               SectionSegmentation(annotated.df, site = 2),
                               SectionSegmentation(annotated.df, site = 3),
                               SectionSegmentation(annotated.df, site = 4))

```

## GetRegexNegex
Empty file to be developed

```{r get_regex_negex}
regex.from.java <- GetRegexNegex(segmented.reports) 
```

## AggregateRegex
Empty file to be develped

```{r aggregate_regex}
regex.df <- AggregateRegex(regex.from.java) # dummy function

# Fake data
regex.df <- segmented.reports %>%
  select(patientID, siteID, imageTypeID) %>%
  mutate(Scoliosis_regex = rbinom(nrow(segmented.reports), 1, 0.3),
         Scoliosis_negex = rbinom(nrow(segmented.reports), 1, 0.2),
         Spondylolisthesis_regex = rbinom(nrow(segmented.reports), 1, 0.3),
         Spondylolisthesis_negex = rbinom(nrow(segmented.reports), 1, 0.2))

```

## CreateTextFeatures

Takes as input the segmented reports, and outputs a data frame with text-based predictors. The generated features have not been tested for compatibility with developed models yet.

Example usage:
```{r create_text_features}
text.dfm <- CreateTextFeatures(segmented.reports, id_col = "patientID") 
```

## ApplyNLPModels

Takes as input the text-based predictors and regex/negex predictors, outputs NLP predictions.

Example usage:
```{r apply_nlp_models}
finding.list <- c("Scoliosis")
nlp.preds <- ApplyNLPModels(finding.list, text.dfm, regex.df)
```


# Java methods summary/progress

