version := "0.1"

name := "checkstyle-check-empty"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.checkstyleSettings
Checkstyle.severityLevel := Some(Checkstyle.CheckstyleSeverityLevel.Error)
