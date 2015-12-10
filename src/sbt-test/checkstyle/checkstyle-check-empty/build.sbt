version := "0.1"

name := "checkstyle-check-empty"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleSeverityLevel := Some(Checkstyle.CheckstyleSeverityLevel.Error)
