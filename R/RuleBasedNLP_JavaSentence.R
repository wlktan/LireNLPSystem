#' Interface to rules-based NLP (Java pipeline) to obtain sentence level predictions.
#'
#' @param segmented.reports The input data frame
#' @param imageid String of column that uniquely identifies each image
#' @param bodyText String of column that indexes body of report text
#' @param impressionText String of column that indexes impression section of report text
#' @param findings_longstring String of findings separated by ";"
#' @keywords load Java
#' @import rJava
#' @import dplyr
#' @export
#' @return A "long" data frame with the columns
#'  Finding
#'  imageid: ID that indexes reports
#'  Sentence: Exact string of the sentence
#'  Section of sentence: Whether the sentence was in body or impression
#'  regex: 1 if there is a keyword in the sentence, otherwise 0
#'  negex: 1 if the keyword is negated, otherwise 0
#'  **NOTE**: regex cannot be 1 if regex = 0
#'  keyword: The keyword that triggered regex = 1
#' @examples
#' RuleBasedNLP_JavaSentence(df, findings_longstring = "facet_degeneration;disc_degeneration")

RuleBasedNLP_JavaSentence <- function(segmented.reports,
                                      imageid = "imageid",
                                      bodyText = "body",
                                      impressionText = "impression",
                                      findings_longstring = paste(c("spondylolisthesis",
                                                                    "annular_fissure",
                                                                    "disc_bulge",
                                                                    "disc_degeneration",
                                                                    "disc_desiccation",
                                                                    "disc_height_loss",
                                                                    "disc_protrusion",
                                                                    "facet_degeneration"),
                                                                  collapse = ";")){
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
           bodyText,
           impressionText,
           findings_longstring) %>%
    lapply(., .jevalArray) %>% # Turns String [][] into data frame in R
    do.call(rbind, .)


  colnames(out.df) <- c(imageid,
                        "Finding",
                        "Sentence",
                        "Section of sentence",
                        "regex",
                        "negex",
                        "keyword")

  return(as.data.frame(out.df))
}
