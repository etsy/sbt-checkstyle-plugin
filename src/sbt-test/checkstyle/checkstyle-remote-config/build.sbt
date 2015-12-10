version := "0.1"

name := "checkstyle-remote-config"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.checkstyleConfig :=
  Some(Checkstyle.CheckstyleConfig.URL("https://raw.githubusercontent.com/etsy/sbt-checkstyle-plugin/master/src/sbt-test/checkstyle/checkstyle/checkstyle-config.xml"))
