version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

(Checkstyle.checkstyle in Test) <<= (Checkstyle.checkstyle in Test) triggeredBy (compile in Test)