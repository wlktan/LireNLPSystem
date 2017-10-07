#' Inputs a dataframe, and outputs a data frame
#'
#' @param regex.df.java The input data frame, which is an output from Java JAR file
#' @keywords load Java
#' @import rJava
#' @export
#' @return df
#' @examples
#' AggregateRegex(df)

AggregateRegex <- function(regex.df.java){

  df <- regex.df.java %>%
    as.data.frame() %>%
    mutate_at(c("regex", "negex"), as.character) %>%
    mutate_at(c("regex", "negex"), as.numeric) %>%
    ### Sentence level predictions: predict positive if there is a at least one non-negated keyword
    mutate(sentence_level_prediction = ifelse(regex == 1 & negex == 0, 1,
                                             ifelse(regex == 1 & negex == 1, -1, 0))) %>%
    
    ### Section level predictions: predict positive if more positive than negative sentences
    select(-Sentence, -keyword, -regex, -negex) %>%
    group_by(Finding, patientID, siteID, imageTypeID, `Section of sentence`) %>%
    summarize_all(sum) %>%
    mutate(section_level_prediction = ifelse(sentence_level_prediction > 0, 1, 
                                             ifelse(sentence_level_prediction < 0, -1, 0))) %>%
    
    ### Impression trumps body
    select(-sentence_level_prediction) %>%
    spread(., `Section of sentence`, section_level_prediction) %>%
    mutate(regex = ifelse(sum(body, impression) > 0, 1, 0),
           negex = ifelse(impression == -1 | (body == -1 & impression == 0), 1, 0)) %>%
    ungroup() %>%
    select(Finding, patientID, siteID, imageTypeID, regex, negex)
    
  
  df.regex <- df %>%
    select(-negex) %>%
    group_by(patientID, siteID, imageTypeID) %>%
    spread(., Finding, regex) %>%
    ungroup()
  
  df.negex <- df %>%
    select(-regex) %>%
    group_by(patientID, siteID, imageTypeID) %>%
    spread(., Finding, negex) %>%
    ungroup()
    
  out.df <- df.regex %>%
    left_join(df.regex, by = c("patientID",
                               "siteID",
                               "imageTypeID"),
              suffix = c("_regex",
                         "_negex"))
  
  return(out.df)
}
