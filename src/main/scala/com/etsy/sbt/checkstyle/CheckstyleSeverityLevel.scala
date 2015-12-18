package com.etsy.sbt.checkstyle

object CheckstyleSeverityLevel extends Enumeration {
  type CheckstyleSeverityLevel = Value
  val Ignore = Value("ignore")
  val Info = Value("info")
  val Warning = Value("warning")
  val Error = Value("error")
}
