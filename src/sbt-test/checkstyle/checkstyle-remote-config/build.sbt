version := "0.1"

name := "checkstyle-remote-config"

organization := "com.etsy"

com.etsy.sbt.Checkstyle.checkstyleSettings
com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleConfig :=
  scala.io.Source.fromURL("https://raw.githubusercontent.com/etsy/sbt-checkstyle-plugin/master/src/sbt-test/checkstyle/checkstyle/checkstyle-config.xml")
