version := "0.1"

name := "checkstyle-integration-test"

organization := "com.etsy"

lazy val root = (project in file(".")).configs(IntegrationTest)

Defaults.itSettings

checkstyleConfigLocation := CheckstyleConfigLocation.File("my-checkstyle-config.xml")
checkstyle in IntegrationTest <<= checkstyleTask(IntegrationTest)
checkstyleOutputFile in IntegrationTest <<= target(_ / "checkstyle-integration-test-report.xml")
