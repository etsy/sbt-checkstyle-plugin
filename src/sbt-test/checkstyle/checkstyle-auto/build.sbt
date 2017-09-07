version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

checkstyleConfigLocation := CheckstyleConfigLocation.File("checkstyle-config.xml")

(checkstyle in Compile) := (checkstyle in Compile).triggeredBy(compile in Compile).value
