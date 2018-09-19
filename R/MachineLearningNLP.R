#' Apply machine-learning NLP for the given list of findings
#'
#' This function applies the feature weights stored in the ml_model_coef.xlsx file (one sheet = one model)
#' To a the input feature matrix text.dfm preprocessed using CreateTextFeatures
#' @param finding.list The vector of string of findings
#' @param text.dfm DFM object of N-grams
#' @param regex.df.wide Regex, negex features
#' @param ml_feature_weights Data frame of feature names and feature weights
#' @param grouping_var Unique identifier of report
#' @keywords ML, machine-learning
#' @import xlsx
#' @export
#' @return A dataframe containing machine-learing predicted probabilities (between 0 and 1)
#' and predicted classes (0 or 1) for each finding in finding.list
#' @examples
#' MachineLearningNLP(finding.list, text.dfm, regex.df.wide, grouping_var = "imageid")

MachineLearningNLP <- function(finding.list,
                        text.dfm,
                        regex.df.wide,
                        ml_feature_weights,
                        grouping_var = "imageid"){

  all.preds <- text.dfm %>%
    dplyr::select(one_of(grouping_var))

  for(f in 1:length(finding.list)){
    ### Run for every finding
    finding <- finding.list[f]
    print(finding)

    ##################### Generate machine-learning NLP predictions ################
    modelFile <- ml_feature_weights %>%
      dplyr::filter(finding_name == finding)

    # X is the matrix of predictors
    X <- text.dfm %>%
      left_join(regex.df.wide, by = grouping_var) %>%
      mutate(Intercept = 1) %>% # Add intercept
      select(one_of(c("Intercept", as.character(modelFile$feature_name))))

    betahat <- modelFile %>% # betahat is the estimated coefficients from machine-learning models
      mutate(feature_name = as.character(feature_name)) %>%
      filter(feature_name %in%  c("Intercept", colnames(X)))

    stopifnot(all(betahat$feature_name == colnames(X))) # names of matrix & vector match?
    print(paste("Num non-zero feature weights =",dim(modelFile)[1]-1))
    print(paste("Num in this feature matirx =",dim(betahat)[1]))

    ### Run Model
    eta = as.matrix(X) %*% as.matrix(betahat$feature_weight) ## compute linear predictor eta = X %*% beta
    phat = exp(eta)/(1 + exp(eta)) # predicted probabilities p = expit(eta))

    ### Generate ML predictions
    all.preds[,paste0(finding,"_mlProb")] <- phat
    all.preds[,paste0(finding,"_mlClass")] <- ifelse(phat > modelFile$feature_weight[which(modelFile$feature_name == "cutoff")], 1,0) # predicted class
  }

  return(all.preds)
}
