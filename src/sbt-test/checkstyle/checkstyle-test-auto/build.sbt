version := "0.1"

name := "checkstyle-test-auto"

organization := "com.etsy"

(checkstyle in Test) := (checkstyle in Test).triggeredBy(compile in Test).value
