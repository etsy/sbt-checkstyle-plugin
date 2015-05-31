package com.etsy.sbt

import java.io.File

/**
 * Configuration for a single XSLT transformation of the checkstyle output file
 */
case class XSLTSettings(xslt: File, output: File)
