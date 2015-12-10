version := "0.1"

name := "checkstyle-check"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfigLocation := Some(Checkstyle.CheckstyleConfig.File("my-checkstyle-config.xml"))
Checkstyle.CheckstyleTasks.checkstyleSeverityLevel := Some(Checkstyle.CheckstyleSeverityLevel.Error)
