version := "0.1"

name := "checkstyle-config-classpath"

organization := "com.etsy"

checkstyleConfigLocation := CheckstyleConfigLocation.Classpath("com/etsy/sbt/google_checks.xml")
