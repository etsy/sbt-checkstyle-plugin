version := "0.1"

name := "checkstyle-check"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.checkstyleSettings
Checkstyle.configLocation := Checkstyle.CheckstyleConfig.File("my-checkstyle-config.xml")
Checkstyle.severityLevel := Some(Checkstyle.CheckstyleSeverityLevel.Error)
