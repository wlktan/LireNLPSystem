#' Creates text-based features from a radiology report corpus
#'
#' This function creates N-gram features from a radiology report corpus, where N-gram
#' @param segmented.reports Input data frame with
#' @param id_col The ID column in segmented.reports, defaults to imageid
#' @param text.cols Vector of findings text column names in segmented.reports, defaults to  c("body","impression")
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
#' CreateTextFeatures(segmented.reports)

CreateTextFeatures <- function(segmented.reports,
                               id_col = "imageid",
                               text.cols = c("body","impression"),
                               all.stop.words = setdiff(stopwords(), c("no", "not", "nor")),
                               finding.dictionary = NULL,
                               min_doc_prop = 0,
                               max_doc_prop = 1,
                               n_gram_length = 1){
  dfm.list <- list()
  ### create feature matrix for each text column
    for(col in 1:length(text.cols)){

      ### create feature matrix
      this.dfm <- quanteda::corpus(as.character(segmented.reports[,text.cols[col]]),
                                   docnames = segmented.reports[,id_col]) %>%
        ## Make feature matrix
        quanteda::dfm(., language = "english",
                      tolower = TRUE,
                      stem = TRUE,
                      remove = all.stop.words,
                      thesaurus = finding.dictionary,
                      valuetype = "glob",
                      remove_punct = TRUE,
                      remove_symbols = TRUE,
                      remove_separators = TRUE,
                      remove_twitter = TRUE,
                      remove_hyphens = FALSE, # if TRUE, self-storage becomes (self, storage)
                      remove_url = TRUE,
                      ngrams = n_gram_length) %>%
        #### remove most frequent and sparse words
        quanteda::dfm_trim(.,
                           min_docfreq = min_doc_prop,
                           max_docfreq = max_doc_prop,
                           termfreq_type = "prop") %>%
        as.data.frame(.) %>%
        dplyr::rename(imageid = document)

      colnames(this.dfm)[2:ncol(this.dfm)] <- paste(toupper(text.cols[col]),
                                                    colnames(this.dfm)[2:ncol(this.dfm)],
                                                    sep = "_")
      print(dim(this.dfm))
      dfm.list[[length(dfm.list) + 1]] <- this.dfm
    }

  ## Combine features into the same document feature matrix
  segmented.reports <- do.call(left_join, dfm.list)

  return(segmented.reports)
}

