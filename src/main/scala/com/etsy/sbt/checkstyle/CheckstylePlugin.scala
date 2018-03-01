package com.etsy.sbt.checkstyle

import com.etsy.sbt.checkstyle.CheckstyleSeverityLevel.CheckstyleSeverityLevel
import sbt.Def.Initialize
import sbt.Keys._
import sbt._

/**
  * An SBT plugin to run checkstyle over Java code
  *
  * @author Andrew Johnson <ajohnson@etsy.com>
  * @author Alejandro Rivera <alejandro.rivera.lopez@gmail.com>
  * @author Joseph Earl <joe@josephearl.co.uk>
  */
object CheckstylePlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements

  object autoImport {
    val checkstyle = TaskKey[Unit]("checkstyle", "Runs checkstyle")
    val checkstyleOutputFile = SettingKey[File]("checkstyle-target", "The location of the generated checkstyle report")
    val checkstyleConfigLocation = SettingKey[CheckstyleConfigLocation]("checkstyle-config-location", "The location of the checkstyle configuration file")
    val checkstyleXsltTransformations = SettingKey[Option[Set[CheckstyleXSLTSettings]]]("xslt-transformations", "An optional set of XSLT transformations to be applied to the checkstyle output")
    val checkstyleSeverityLevel = SettingKey[Option[CheckstyleSeverityLevel]]("checkstyle-severity-level", "Sets the severity levels which should fail the build")

    val CheckstyleConfigLocation = com.etsy.sbt.checkstyle.CheckstyleConfigLocation
    val CheckstyleSeverityLevel = com.etsy.sbt.checkstyle.CheckstyleSeverityLevel
    val CheckstyleXSLTSettings = com.etsy.sbt.checkstyle.CheckstyleXSLTSettings

    /**
      * Runs checkstyle
      *
      * @param conf The configuration (Compile or Test) in which context to execute the checkstyle command
      */
    def checkstyleTask(conf: Configuration): Initialize[Task[Unit]] = Def.task {
      Checkstyle.checkstyle((javaSource in conf).value, (resources in Compile).value, (checkstyleOutputFile in conf).value, (checkstyleConfigLocation in conf).value,
        (checkstyleXsltTransformations in conf).value, (checkstyleSeverityLevel in conf).value, streams.value)
    }
  }

  // scalastyle:off import.grouping
  import autoImport._
  // scalastyle:on import.grouping

  private lazy val commonSettings: Seq[Def.Setting[_]] = Seq(
    checkstyleXsltTransformations := None,
    checkstyleSeverityLevel := None
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    checkstyleOutputFile := target.value / "checkstyle-report.xml",
    checkstyleOutputFile in Test := target.value / "checkstyle-test-report.xml",
    checkstyleConfigLocation := com.etsy.sbt.checkstyle.CheckstyleConfigLocation.File("checkstyle-config.xml"),
    checkstyleConfigLocation in Test := checkstyleConfigLocation.value,
    checkstyle := checkstyleTask(Compile).value,
    checkstyle in Test := checkstyleTask(Test).value
  ) ++ commonSettings
}
