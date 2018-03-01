version := "0.1"

name := "checkstyle-integration-test"

organization := "com.etsy"

lazy val root = (project in file(".")).configs(IntegrationTest)

Defaults.itSettings

checkstyleConfigLocation := CheckstyleConfigLocation.File("my-checkstyle-config.xml")
checkstyle in IntegrationTest := checkstyleTask(IntegrationTest).value
checkstyleOutputFile in IntegrationTest := target.value / "checkstyle-integration-test-report.xml"
