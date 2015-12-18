version := "0.1"

name := "checkstyle-integration-test"

organization := "com.etsy"

lazy val root = (project in file(".")).configs(IntegrationTest)

Defaults.itSettings

import com.etsy.sbt.checkstyle._

Checkstyle.configLocation := Checkstyle.CheckstyleConfig.File("my-checkstyle-config.xml")
Checkstyle.checkstyleSettings ++ Seq(
  Checkstyle.configLocation := Checkstyle.CheckstyleConfig.File("test-checkstyle-config.xml"),
  Checkstyle.checkstyle       in IntegrationTest <<= Checkstyle.checkstyleTask(IntegrationTest),
  Checkstyle.outputFile in IntegrationTest <<= target(_ / "checkstyle-integration-test-report.xml")
)
