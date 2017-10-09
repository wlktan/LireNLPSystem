#' Inputs a dataframe, and outputs a data frame
#'
#' @param regex.df.java The input data frame, which is an output from Java JAR file
#' @keywords load Java
#' @import rJava
#' @export
#' @return df
#' @examples
#' AggregateRegex(df)
#' 

AggregateRegex <- function(regex.df.java){
  
  # Aggregate function from sentence to section
  PredictSectionRegex <- function(regex){
    out <- ifelse(max(regex) == 1, 1, 0) 
    return(out)
  }
  
  PredictSectionNegex <- function(negex){
    out <- ifelse(min(negex) == -1, -1, 0) 
    return(out)
  }

  df <- regex.df.java %>%
    as.data.frame() %>%
    mutate_at(c("regex", "negex"), as.character) %>%
    mutate_at(c("regex", "negex"), as.numeric) %>%
    
    ### Section level predictions
    select(-Sentence, -keyword) %>%
    group_by(Finding, imageid, siteID, imageTypeID, `Section of sentence`) %>%
    summarize(regex_section = PredictSectionRegex(regex),
              negex_section = PredictSectionNegex(negex)) %>%
    mutate(section_level_prediction = ifelse(regex_section == 1 & negex_section == 0, 1,
                                             ifelse(regex_section == 1 & negex_section == 1, -1, 
                                                    0))) %>%
    select(-regex_section, -negex_section) %>%
    
    ### Impression trumps body
    spread(., `Section of sentence`, section_level_prediction) %>%
    mutate(rbp = ifelse(body == 1 | impression == 1, 1, 
                        ifelse(impression == -1 | (body == -1 & impression == 0), -1,
                               0)),
           regex = ifelse(rbp == 1, 1, 0),
           negex = ifelse(rbp == -1, 1, 0)) %>%
    ungroup() %>%
    select(Finding, imageid, siteID, imageTypeID, regex, negex)
    
  
  df.regex <- df %>%
    select(-negex) %>%
    group_by(imageid, siteID, imageTypeID) %>%
    spread(., Finding, regex) %>%
    ungroup()
  
  df.negex <- df %>%
    select(-regex) %>%
    group_by(imageid, siteID, imageTypeID) %>%
    spread(., Finding, negex) %>%
    ungroup()
    
  out.df <- df.regex %>%
    left_join(df.negex, by = c("imageid",
                               "siteID",
                               "imageTypeID"),
              suffix = c("_regex",
                         "_negex"))
  
  return(out.df)
}
