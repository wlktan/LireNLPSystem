#' Section Segmentation of Radiology Text Reports
#'
#' This function segments the imagereporttext column of a data frame into columns containing text
#' history, exam, comparison, technique, finding, impression
#' @param df Input data frame
#' @param site 1= , 2= , 3=, 4=; defaults to 2
#' @param hst_rgx Defaults to regex for Kaiser
#' @param exam_rgx Defaults to regex for Kaiser
#' @param comparison_rgx Defaults to regex for Kaiser
#' @param technique_rgx Defaults to regex for Kaiser
#' @param body_rgx Defaults to regex for Kaiser
#' @param imp_rgx Defaults to regex for Kaiser
#' @param datetime_rgx
#' @param liretext_rgx Defaults to regular expressions for LIRE macro; don't change unless there's a good reason to.
#' @keywords section segmentation
#' @import dplyr
#' @import tidyr
#' @export
#' @return The input data frame with additional columns exam, comparison, technique, finding, impression.
#' Finding and impression columns are always non-empty. If separation is not possible, the entire imagereporttext is returned in finding.
#' @examples
#' SectionSegmentation(input.df, site = 2)

SectionSegmentation <- function(df, 
                                site = NULL,
                                hst_rgx = NULL,
                                exam_rgx = NULL,
                                comparison_rgx = NULL,
                                technique_rgx = NULL,
                                body_rgx = NULL,
                                imp_rgx = NULL,
                                datetime_rgx = NULL,
                                liretext_rgx = "(Among\\s*people\\s*(over|under|between)\\s*the\\s*age\\s*of.*$)|(Some\\s*findings\\s*are\\s*so\\s*common.*$)|(The\\s*following\\s*findings\\s*are\\s*so\\s*common\\s*in\\s*normal.*$)"){
  
  if(is.null(site) & (is.null(body_rgx) | is.null(imp_rgx)))
    print("No site or regular expression supplied; defaulting to parameters for site = 2 (Kaiser Permanente).")
  
  
  # Create regular expressions of heading by site
  if(site == 1){
    hst_rgx <- "(\\[\\s*HST\\s*\\]\\s*:)|(HISTORY\\s*:)|(History\\s*:)"
    exam_rgx <- "(EXAMINATION\\s*:)|(EXAM\\s*:)|(Exam\\s*:)|(Examination\\s*:)"
    comparison_rgx <- "(COMPARISON\\s*:)|(Comparison\\s*:)|(COMPARISONS\\s*:)"
    technique_rgx <- "(TECHNIQUE\\s*:)|(Technique\\s*:)"
    body_rgx <- "(FINDINGS\\s*:)|(Findings\\s*:)|(FINDING\\s*:)|(Finding\\s*:)"
    imp_rgx <- "(IMPRESSION\\s*:)|(Impression\\s*:)|(IMPRESSIONS\\s*:)|(IMPRESSION\\s\\.\\s)"
    datetime_rgx <- "Date\\s*:\\s*(\\d){2}\\/(\\d){2}\\/(\\d){4}\\s*Time\\s*:(\\d){1,2}:(\\d){2}(PM)?$"
  }
  
  if(site == 2 | is.null(site)){
    hst_rgx <- "\\*\\*\\s*HISTORY\\s*\\*\\*\\s*:"
    exam_rgx <- "(EXAMINATION\\s*:)|(EXAM\\s*:)|(Exam\\s*:)|(Examination\\s*:)"
    comparison_rgx <- "(COMPARISON\\s*:)|(Comparison\\s*:)|(COMPARISONS\\s*:)"
    technique_rgx <- "(TECHNIQUE\\s*:)|(Technique\\s*:)"
    body_rgx <- "\\*\\*\\s*FINDINGS\\s*\\*\\*\\s*:" 
    imp_rgx <- "\\*\\*\\s*IMPRESSION\\s*\\*\\*\\s*:"
    datetime_rgx <- "Date\\s*:\\s*(\\d){2}\\/(\\d){2}\\/(\\d){4}\\s*Time\\s*:(\\d){1,2}:(\\d){2}(PM)?$"
  }
  
  if(site == 3){
    hst_rgx <- "(Clinical\\s*History\\s*:)(\\[\\s*HST\\s*\\]\\s*:)|(HISTORY\\s*:)|(History\\s*:)"
    exam_rgx <- "(EXAMINATION\\s*:)|(EXAM\\s*:)|(Exam\\s*:)|(Examination\\s*:)"
    comparison_rgx <- "(COMPARISON\\s*:)|(Comparison\\s*:)|(COMPARISONS\\s*:)"
    technique_rgx <- "(TECHNIQUE\\s*:)|(Technique\\s*:)"
    body_rgx <- "(Findings/impression\\s*:)|(FINDINGS\\s*:)|(Findings\\s*:)|((?<!\\d\\.\\s)Finding\\s)|((?<!\\d\\.\\s)Findings\\s)|(Lumbosacral\\s*spine\\s*findings\\s*:)" 
    imp_rgx <- "(IMPRESSIO\\s*:)|(\\s{1}impression\\s*:)|(IMPRESSION\\s*:)|(IMPRESSIONS\\s*:)|(Impression\\s*:)|(Impression\\s)|(Impressions\\s*:)|(Combined\\s*impressions\\s*:)|(Combined\\s*impression\\s*:)|(Conclusions\\s*:)|(Conclusion\\s*:)|(Pression\\s*:)|(Pressions\\s*:)"
    datetime_rgx <- "Date\\s*:\\s*(\\d){2}\\/(\\d){2}\\/(\\d){4}\\s*Time\\s*:(\\d){1,2}:(\\d){2}(PM)?$"
  }
  
  if(site == 4){
    hst_rgx <- "(Clinical\\s*History\\s*:)(\\[\\s*HST\\s*\\]\\s*:)|(HISTORY\\s*:)|(History\\s*:)"
    exam_rgx <- "(EXAMINATION\\s*:)|(EXAM\\s*:)|(Exam\\s*:)|(Examination\\s*:)"
    comparison_rgx <- "(COMPARISON\\s*:)|(Comparison\\s*:)|(COMPARISONS\\s*:)|(Indication\\s*:)|(Indications\\s*:)|(INDICATION\\s:)"
    technique_rgx <- "(TECHNIQUE\\s*:)|(Technique\\s*:)"
    body_rgx <- "(Findings\\s*/Impression\\s*:)|(FINDINGS\\s*/impression\\s*:)|(Findings\\s*/\\s*impression\\s*:)|(\\sFindings\\s*:)|(FINDINGS\\s*:)|(RESULT\\s*:)|(RESULTS\\s*:)|(Results\\s*:)|(Result\\s:)|(THORACIC\\s*SPINE\\s*:)" 
    imp_rgx <- "(Impression\\s*:)|(\\sImpression\\s*:)|(IMPRESSION:)"
    datetime_rgx <- "Date\\s*:\\s*(\\d){2}\\/(\\d){2}\\/(\\d){4}\\s*Time\\s*:(\\d){1,2}:(\\d){2}(PM)?$"
  }
  
  df.site <- subset(df, siteID == site) %>%
    
    #### Remove LIRE intervention text
    mutate(imagereporttext = gsub(liretext_rgx, "", imagereporttext, perl = TRUE)) %>% 
    
    ### Insert <TAGS>
    mutate(tagged = gsub(hst_rgx, "<TAG_BEFORE_HISTORY>", imagereporttext, perl = TRUE),
           tagged = gsub(exam_rgx, "<TAG_BEFORE_EXAM>", tagged, perl = TRUE),
           tagged = gsub(comparison_rgx, "<TAG_BEFORE_COMPARISON>", tagged, perl = TRUE),
           tagged = gsub(technique_rgx, "<TAG_BEFORE_TECHNIQUE>", tagged, perl = TRUE),
           tagged = gsub(body_rgx, "<TAG_BEFORE_BODY>", tagged, perl = TRUE), 
           tagged = gsub(imp_rgx, "<TAG_BEFORE_IMPRESSION>", tagged, perl = TRUE),
           tagged = gsub(datetime_rgx, "<TAG_DATETIME>", tagged, perl = TRUE)) %>%
    
    ### Split by <TAGS>
    separate(., tagged, into = c("impression", "datetime"), sep = "<TAG_DATETIME>") %>%
    separate(., impression, into = c("body", "impression"), sep = "<TAG_BEFORE_IMPRESSION>") %>%
    separate(., body, into = c("technique", "body"), sep = "<TAG_BEFORE_BODY>") %>%
    separate(., technique, into = c("comparison", "technique"), sep = "<TAG_BEFORE_TECHNIQUE>") %>%
    separate(., comparison, into = c("exam", "comparison"), sep = "<TAG_BEFORE_COMPARISON>") %>%
    separate(., exam, into = c("history", "exam"), sep = "<TAG_BEFORE_EXAM>") %>%
    mutate(history = gsub("^<TAG_BEFORE_HISTORY>", "", history, perl = TRUE),
           history = gsub("^(\\s*\\*\\*NAME\\s*\\s*)$", "", history, perl = TRUE))
  
  
  return(df.site)
}
