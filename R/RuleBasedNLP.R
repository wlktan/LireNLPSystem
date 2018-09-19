#' Inputs a dataframe, and outputs a data frame
#'
#' @param regex.df.java The input data frame, which is an output from Java JAR file
#' @keywords load Java
#' @import rJava
#' @export
#' @return List of
#' regex.df.wide: A data frame with nrow = unique imageids of regex.df.java, having regex (keyword present)
#' and negex (keyword present but negated)
#' columns for each of the findings
#' rules.nlp.df: A data frame with nrow = unique imageids of regex.df.java, having columns for the
#' report level rules based NLP predictions for each finding
#' @examples
#' RuleBasedNLP(df)
#'

RuleBasedNLP <- function(regex.df.java){

  # Aggregate function from sentence to section
  PredictSectionRegex <- function(regex){
    out <- ifelse(max(regex) == 1, 1, 0)
    return(out)
  }

  PredictSectionNegex <- function(negex){
    out <- ifelse(min(negex) == -1, -1, 0)
    return(out)
  }

  regex.df.temp <- regex.df.java %>%
    as.data.frame() %>%
    mutate_at(c("regex", "negex"), as.character) %>%
    mutate_at(c("regex", "negex"), as.numeric) %>%

    ### Section level predictions
    select(-Sentence, -keyword) %>%
    group_by(Finding, imageid, `Section of sentence`) %>%
    summarize(regex_section = PredictSectionRegex(regex),
              negex_section = PredictSectionNegex(negex)) %>%
    mutate(section_level_prediction = ifelse(regex_section == 1 & negex_section == 0, 1,
                                             ifelse(regex_section == 1 & negex_section == 1, -1, 0))) %>%
    select(-regex_section, -negex_section) %>%

    ### Impression trumps body
    spread(., `Section of sentence`, section_level_prediction) %>%
    mutate(rules_nlp = ifelse(body == 1 | impression == 1, 1,
                              ifelse(impression == -1 | (body == -1 & impression == 0), -1,
                                     0)),
           report_regex = ifelse(rules_nlp == 1, 1, 0),
           report_negex = ifelse(rules_nlp == -1, 1, 0)) %>%
    ungroup() %>%
    select(Finding, imageid, report_regex, report_negex, rules_nlp)


  ### Get regex, negex
  finding.regex.df <- regex.df.temp %>%
    select(-report_negex, -rules_nlp) %>%
    group_by(imageid) %>%
    spread(., Finding, report_regex) %>%
    ungroup()

  finding.negex.df <- regex.df.temp %>%
    select(-report_regex, -rules_nlp) %>%
    group_by(imageid) %>%
    spread(., Finding, report_negex) %>%
    ungroup()

  regex.df.wide <- finding.regex.df %>%
    left_join(finding.negex.df, by = c("imageid"),
              suffix = c("_regex", "_negex"))

  ### Rules NLP DF
  rules.nlp.df <- regex.df.temp %>%
    select(-report_regex, -report_negex) %>%
    mutate(Finding = paste0(Finding, "_rules")) %>%
    group_by(imageid) %>%
    spread(., Finding, rules_nlp) %>%
    ungroup()

  return(list(regex.df.wide = regex.df.wide,
              rules.nlp.df = rules.nlp.df))
}
