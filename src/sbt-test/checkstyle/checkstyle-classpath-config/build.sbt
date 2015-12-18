version := "0.1"

name := "checkstyle-classpath-config"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.checkstyleSettings
Checkstyle.configLocation := Checkstyle.CheckstyleConfig.Classpath("com/etsy/sbt/google_checks.xml")
