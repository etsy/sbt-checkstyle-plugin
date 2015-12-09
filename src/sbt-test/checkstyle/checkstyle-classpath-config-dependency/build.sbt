version := "0.1"

name := "checkstyle-classpath-config"

organization := "com.etsy"

libraryDependencies += "com.puppycrawl.tools" % "checkstyle" % "6.13"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfig := Checkstyle.ConfigSource.Classpath("google_checks.xml")
