version := "0.1"

name := "checkstyle-classpath-config"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfig := Checkstyle.ConfigSource.Classpath("com/etsy/sbt/google_checks.xml")
