version := "0.1"

name := "checkstyle-config-classpath-dependency"

organization := "com.etsy"

libraryDependencies += "com.puppycrawl.tools" % "checkstyle" % "6.13"

checkstyleConfigLocation := CheckstyleConfigLocation.Classpath("google_checks.xml")
