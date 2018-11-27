#' Loads a Java executable file 
#'
#' This function loads a java executable file contained in inst/java folder
#' @param libname name of library
#' @param pkgname name of package
#' @keywords load Java
#' @import rJava
#' @examples
#' .onLoad()

.onLoad <- function(libname, pkgname) {
  .jpackage(pkgname, lib.loc = libname)
}