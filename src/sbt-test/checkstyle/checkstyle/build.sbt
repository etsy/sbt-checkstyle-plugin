version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfig := file("checkstyle-config.xml")
