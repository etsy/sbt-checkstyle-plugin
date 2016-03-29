version := "0.1"

name := "checkstyle-check"

organization := "com.etsy"

checkstyleConfigLocation := CheckstyleConfigLocation.File("my-checkstyle-config.xml")
checkstyleSeverityLevel := Some(CheckstyleSeverityLevel.Error)
