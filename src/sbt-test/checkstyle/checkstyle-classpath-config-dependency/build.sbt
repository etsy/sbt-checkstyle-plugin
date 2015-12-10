version := "0.1"

name := "checkstyle-classpath-config-dependency"

organization := "com.etsy"

libraryDependencies += "com.puppycrawl.tools" % "checkstyle" % "6.13"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfigLocation := Some(Checkstyle.CheckstyleConfig.Classpath("google_checks.xml"))
