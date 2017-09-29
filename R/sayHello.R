#' Say hello to a user
#'
#' This is a test function to test the rJava interface
#' whenever changes to Java code is made
#' This code will be removed from final package release
#' @keywords load Java
#' @import rJava
#' @export
#' @return A string greeting the user as called from the Java method
#' @examples
#' sayHello("Jason")

sayHello <- function(username){
  java.program <- .jnew("RuleBasedNLP", check = TRUE) # create instance of RuleBasedNLP class in saved JAR
  out <- .jcall(obj = java.program, 
                returnSig = "S", # outputs to a string
                method = "SayHello", # invoke the SayHello method in RuleBasedNLP class
                username)
  return(out)
}