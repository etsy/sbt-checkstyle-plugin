version := "0.1"

name := "checkstyle-integration-test"

organization := "com.etsy"

lazy val root = (project in file(".")).configs(IntegrationTest)

Defaults.itSettings

import com.etsy.sbt._
com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleConfig := file("my-checkstyle-config.xml")
com.etsy.sbt.Checkstyle.checkstyleSettings ++ Seq(
  Checkstyle.CheckstyleTasks.checkstyleConfig := file("test-checkstyle-config.xml"),
  Checkstyle.CheckstyleTasks.checkstyle       in IntegrationTest <<= Checkstyle.checkstyleTask(IntegrationTest),
  Checkstyle.CheckstyleTasks.checkstyleTarget in IntegrationTest <<= target(_ / "checkstyle-integration-test-report.xml")
)
