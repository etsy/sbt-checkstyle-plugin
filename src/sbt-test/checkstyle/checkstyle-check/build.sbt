version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

com.etsy.sbt.Checkstyle.checkstyleSettings
com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleConfig := file("my-checkstyle-config.xml")

import com.etsy.sbt.Checkstyle.CheckstyleSeverityLevel._
com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleSeverityLevel := Some(CheckstyleSeverityLevel.Error)
