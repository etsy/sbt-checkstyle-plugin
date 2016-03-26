version := "0.1"

name := "checkstyle-config-remote"

organization := "com.etsy"

checkstyleConfigLocation :=
  CheckstyleConfigLocation.URL("https://raw.githubusercontent.com/etsy/sbt-checkstyle-plugin/master/src/sbt-test/checkstyle/checkstyle/checkstyle-config.xml")
