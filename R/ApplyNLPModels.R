#' ApplyNLPModels
#'
#' This function segments the imagereporttext column of a data frame into columns containing text
#' history, exam, comparison, technique, finding, impression
#' @param df Input data frame
#' @param site 1= , 2= , 3=, 4=; defaults to 2
#' @param chunk
#' @param all.predsput_filepath
#' @keywords chunk file
#' @import xlsx
#' @export
#' @return A folder of files as in all.predsput_filepath that contains dataChunk_1.csv, ...
#' @examples


ApplyNLPModels <- function(finding.list, text.dfm, regex.df){
  
  all.preds <- regex.df %>%
    select(one_of(c("patientID", "siteID", "imageTypeID")))
                  
  for(f in 1:length(finding.list)){
    ### Run for every finding
    finding <- finding.list[f]
    print(finding)
    
    ##################### Generate machine-learning NLP predictions ################
    modelFile <- try(read.xlsx("ml_model_coef.xlsx",
                               sheetName = finding,
                               header = TRUE))
    
    # X is the matrix of predictors
    X <- regex.df %>% 
      select(one_of(c("patientID", "siteID", "imageTypeID", paste0(finding, "_regex"), paste0(finding, "_negex")))) %>%
      left_join(text.dfm, by = "patientID") %>%
      mutate(Intercept = 1) %>% # Add intercept
      select(one_of(c("Intercept", as.character(modelFile$predictors))))
    
    betahat <- modelFile %>% # betahat is the estimated coefficients from machine-learning models
      mutate(predictors = as.character(predictors)) %>%
      filter(predictors %in%  c("Intercept",colnames(X)))
    
    print(all(betahat$predictors == colnames(X))) # names of matrix & vector match?
    print(paste("Number of coefficients from model =",dim(modelFile)[1]-1))
    print(paste("Number of coefficients from data =",dim(betahat)[1]))
    
    
    ### Run Model 
    eta <- as.matrix(X) %*% as.matrix(betahat$coef) ## compute linear predictor eta = X %*% beta
    phat <- exp(eta)/(1 + exp(eta)) # predicted probabilities p = expit(eta)
    
    ### Generate ML predictions
    all.preds[,paste0(finding,"_mlProb")] <- round(phat,8) # predicted probabilities
    all.preds[,paste0(finding,"_mlClass")] <- ifelse(phat > modelFile$coef[which(modelFile$predictors == "cutoff")], 1,0) # predicted class
    
    ### Generate RB predictions
    all.preds[,paste0(finding,"_rules")] <- ifelse(regex.df[,paste0(finding, "_regex")] == 1 & regex.df[,paste0(finding, "_negex")] == 0, 1, 0)
      
    
    ### rule-based v.s. machine-learning binary predictions
    # print(table(all.preds[,paste0(finding,"_rules")],
    #             all.preds[,paste0(finding,"_mlClass")]))
  }
  
  return(all.preds)
}
