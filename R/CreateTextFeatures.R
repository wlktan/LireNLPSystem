#' Creates text-based features from a radiology report corpus
#'
#' This function creates text-based features from a radiology report corpus
#' @param text.df Input data frame with 
#' @param id_col The ID column in text.df, defaults to imageid
#' @param text.cols The text columns in text.df, defaults to c("body", "impression")
#' @param all.stop.words List of stop words, defaults to English stopword list excluding negation
#' @param finding.dictionary Dictionary object to map findings, defaults to NULL
#' @param min_count Minimum count of total word occurence across all documents/reports
#' @param min_doc Minimum count of number of documents for which word occurs
#' @param n_gram_length Unigram, bigram, or trigram features; defaults to 3 (trigrams)
#' @keywords CreateTextFeatures
#' @import quanteda
#' @export
#' @return A document frequency matrix with each row as a unique report, 
#' each column is a feature, and the cells are the counts in the document.
#' @examples
#' CreateTextFeatures(text.df)

CreateTextFeatures <- function(text.df,
                               id_col = "imageid",
                               text.cols = c("body", "impression"),
                               all.stop.words = setdiff(stopwords(), c("no", "not", "nor")),
                               finding.dictionary = NULL,
                               min_count = 50,
                               min_doc = 5,
                               n_gram_length = 3){
  
  text.df <- as.data.frame(text.df)
  dfm.list <- list()
  ### create feature matrix for each text column
    for(col in 1:length(text.cols)){
      
      ### create feature matrix
      this.dfm <- corpus(as.character(text.df[,text.cols[col]]),
                            docnames = text.df[,id_col]) %>%
        ## Make feature matrix
        dfm(., language = "english",
            tolower = TRUE,
            stem = TRUE,
            remove = all.stop.words,
            thesaurus = finding.dictionary,
            valuetype = "glob",
            # arguments to tokenize()
            remove_numbers = TRUE,
            remove_punct = TRUE,
            remove_symbols = TRUE,
            remove_separators = TRUE,
            remove_twitter = TRUE,
            remove_hyphens = FALSE, # if TRUE, self-storage becomes (self, storage)
            remove_url = TRUE,
            ngrams = n_gram_length) %>%
        #### remove most frequent and sparse words
        dfm_trim(.,
                 min_count = min_count,
                 min_docfreq = min_doc,
                 max_docfreq = dim(text.df)[1]/2) %>%
        as.data.frame(.)
      
      colnames(this.dfm) <- paste(toupper(text.cols[col]), colnames(this.dfm), sep = "_")
      this.dfm[,id_col] <- rownames(this.dfm)

      print(dim(this.dfm))
      dfm.list[[length(dfm.list) + 1]] <- this.dfm
    }
  
  ## Combine features into the same document feature matrix
  text.dfm <- do.call(left_join, dfm.list)
  colnames(text.dfm) <- gsub("IMPRESSION", "IMP", colnames(text.dfm))
  
  return(text.dfm)
}

