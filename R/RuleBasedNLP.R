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

  regex.df.java <- as.data.frame(regex.df.java) %>%
    mutate_at(c("regex", "negex"), as.character) %>%
    mutate_at(c("regex", "negex"), as.numeric)

  ###### Data frame of regex & negex variables for input into machine-learning
  temp <- regex.df.java %>%
    select(-Sentence, -keyword) %>%
    group_by(Finding, imageid) %>%
    summarize(report_regex = max(regex),
              report_negex = max(negex))

  rgx <- temp %>%
    select(-report_negex) %>%
    group_by(imageid) %>%
    spread(., Finding, report_regex) %>%
  ungroup()

  ngx <- temp %>%
    select(-report_regex) %>%
    group_by(imageid) %>%
    spread(., Finding, report_negex) %>%
  ungroup()

  regex.df.wide <- rgx %>%
    left_join(ngx, by = c("imageid"),
              suffix = c("_regex", "_negex"))

  ###### Rules NLP aggregation
  rbnlp.tb <- data.frame(body = c(1,1,1,0,0,0,-1,-1,-1),
                         impression = c(1,0,-1,1,0,-1,1,0,-1),
                         rules_nlp = c(1,1,-1,1,0,-1,1,-1,-1))

  rules.nlp.df <- regex.df.java %>%
    ### Section level predictions
    mutate(sentence_level_prediction = ifelse(regex == 1 & negex == 0, 1,
                                              ifelse(regex == 1 & negex == 1, -1, 0))) %>%
    select(-Sentence, -keyword, -regex, -negex) %>%
    group_by(Finding, imageid, `Section of sentence`) %>%
    summarize(section_level_prediction = ifelse(max(sentence_level_prediction) == 1, 1,
                                                ifelse(min(sentence_level_prediction) == -1, -1, 0))) %>%
    ### Impression trumps body
    spread(., `Section of sentence`, section_level_prediction) %>%
    ungroup() %>%
    left_join(rbnlp.tb, by = c("body", "impression")) %>%
    select(Finding, imageid, rules_nlp) %>%
    mutate(Finding = paste0(Finding, "_rules")) %>%
    group_by(imageid) %>%
    spread(., Finding, rules_nlp) %>%
    ungroup()

  return(list(regex.df.wide = regex.df.wide,
              rules.nlp.df = rules.nlp.df))
}
