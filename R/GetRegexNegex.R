#' Inputs a dataframe, and outputs a data frame
#'
#' @param segmented.reports The input data frame
#' @param imageid String of column that indexes report images; defaults to patientID
#' @param siteID String of column that indexes study site; defaults to siteID
#' @param imageTypeID String of column that indexes imaging modality; defaults to imageTypeID
#' @param bodyText String of column that indexes body of report text
#' @param bodyText String of column that indexes impression sectino of report text
#' @keywords load Java
#' @import rJava
#' @import dplyr
#' @export
#' @return A "long" data frame with the columns
#'  Finding
#'  imageid: ID that indexes reports
#'  siteID: study site
#'  imageTypeID: imaging modality
#'  Sentence: Exact string of the sentence
#'  Section of sentence: Whether the sentence was in body or impression
#'  regex: 1 if there is a keyword in the sentence, otherwise 0
#'  negex: 1 if the keyword is negated, otherwise 0
#'  **NOTE**: regex cannot be 1 if regex = 0
#'  keyword: The keyword that triggered regex = 1
#' @examples
#' GetRegexNegex(df)

GetRegexNegex <- function(segmented.reports,
                          imageid = "patientID",
                          siteID = "siteID",
                          imageTypeID = "imageTypeID",
                          bodyText = "body",
                          impressionText = "impression"){
  java.program <- .jnew("edu.uw.biostat.lire.RuleBasedNLP.RuleBasedNLP", check = TRUE) # Need the whole package path to the class file
  #.jmethods(java.program) # shows all the methods
  
  segmented.reports <- segmented.reports %>%
    mutate_all(as.character) %>% # each col in segmented.reports needs to be a Java type (number or string); factors not allowed
    rbind(colnames(.), .)

  out.df <- .jarray(lapply(segmented.reports, .jarray)) %>% # Turn df into Java object
    .jcast(., new.class = "[[Ljava/lang/String;") %>% # Cast to String[][]
    .jcall(obj = java.program, 
           returnSig = "[[Ljava/lang/String;", # Outputs a String[][]
           method = "GetRegexNegex", 
           segmented.reports,
           imageid,
           siteID,
           imageTypeID,
           bodyText,
           impressionText) %>%
    lapply(., .jevalArray) %>% # Turns String [][] into data frame in R
    do.call(rbind, .)
  
  
  colnames(out.df) <- c(imageid, 
                        siteID, 
                        imageTypeID, 
                        "Finding", 
                        "Sentence", 
                        "Section of sentence", 
                        "regex", 
                        "negex", 
                        "keyword")
  
  out.df <- as.data.frame(out.df)
  
  ## Need table(out.df$regex, out.df$negex) to have 0 in topleft corner

  return(out.df)
}