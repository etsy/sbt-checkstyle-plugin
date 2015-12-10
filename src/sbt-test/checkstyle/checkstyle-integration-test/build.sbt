version := "0.1"

name := "checkstyle-integration-test"

organization := "com.etsy"

lazy val root = (project in file(".")).configs(IntegrationTest)

Defaults.itSettings

import com.etsy.sbt.Checkstyle
Checkstyle.CheckstyleTasks.checkstyleConfigLocation := Some(Checkstyle.CheckstyleConfig.File("my-checkstyle-config.xml"))
Checkstyle.checkstyleSettings ++ Seq(
  Checkstyle.CheckstyleTasks.checkstyleConfigLocation := Some(Checkstyle.CheckstyleConfig.File("test-checkstyle-config.xml")),
  Checkstyle.CheckstyleTasks.checkstyle       in IntegrationTest <<= Checkstyle.checkstyleTask(IntegrationTest),
  Checkstyle.CheckstyleTasks.checkstyleTarget in IntegrationTest <<= target(_ / "checkstyle-integration-test-report.xml")
)
