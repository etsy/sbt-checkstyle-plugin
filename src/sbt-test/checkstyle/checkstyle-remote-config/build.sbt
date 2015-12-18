version := "0.1"

name := "checkstyle-remote-config"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.configLocation :=
  CheckstyleConfig.URL("https://raw.githubusercontent.com/etsy/sbt-checkstyle-plugin/master/src/sbt-test/checkstyle/checkstyle/checkstyle-config.xml")
