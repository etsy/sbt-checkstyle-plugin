package com.etsy.sbt

import javax.xml.transform.stream.StreamSource

import com.puppycrawl.tools.checkstyle.Main.{main => CheckstyleMain}
import net.sf.saxon.s9api.Processor
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
object Checkstyle extends Plugin {

  import com.etsy.sbt.Checkstyle.CheckstyleTasks._

  object CheckstyleTasks {
    val checkstyle = TaskKey[Unit]("checkstyle", "Runs checkstyle")
    val checkstyleCheck = TaskKey[Unit]("checkstyle-check", "Runs checkstyle and fails the task if issues are found")
    val checkstyleTarget = SettingKey[File]("checkstyle-target", "The location of the generated checkstyle report")
    val checkstyleConfig = SettingKey[File]("checkstyle-config", "The location of the checkstyle configuration file")
    val xsltTransformations = SettingKey[Option[Set[XSLTSettings]]]("xslt-transformations", "An optional set of XSLT transformations to be applied to the checkstyle output")
    val checkstyleCheckSeverityLevel = SettingKey[String]("checkstyle-check-level", "Sets the severity levels which should fail the build")
  }

  /**
    * Runs checkstyle
    *
    * @param conf The configuration (Compile or Test) in which context to execute the checkstyle command
    */
  def checkstyleTask(conf: Configuration): Initialize[Task[Unit]] = Def.task {
    val configFile = (checkstyleConfig in conf).value.getAbsolutePath
    val outputFile = (checkstyleTarget in conf).value.getAbsolutePath
    val source = (javaSource in conf).value.getAbsolutePath

    checkstyleMain(configFile, outputFile, source, xsltTransformations.value)
  }

  /**
    * Runs checkstyle and fails the task if issues are found.
    *
    * Use setting 'checkstyleCheckSeverityLevel' to define what type of issues should break the build.
    *
    * @param conf The configuration (Compile or Test) in which context to execute the checkstyle command
    */
  def checkstyleCheckTask(conf: Configuration): Initialize[Task[Unit]] = Def.task {
    val configFile = (checkstyleConfig in conf).value.getAbsolutePath
    val outputFile = (checkstyleTarget in conf).value.getAbsolutePath
    val source = (javaSource in conf).value.getAbsolutePath

    checkstyleMain(configFile, outputFile, source, xsltTransformations.value)

    if (file(outputFile).exists) {
      val log = streams.value.log
      val report = scala.xml.XML.loadFile(file(outputFile))
      val checkstyleSeverityLevels = Seq("ignore", "info", "warning", "error")
      val appliedCheckstyleSeverityLevels = checkstyleSeverityLevels.indexOf(checkstyleCheckSeverityLevel.value) match {
        case i if i == -1 => Seq("error") // unknown level supplied, default to just error
        case i => checkstyleSeverityLevels.drop(i)
      }
      var issuesFound = 0
      (report \ "file").foreach { file =>
        (file \ "error").foreach { error =>
          val severity: String = error.attribute("severity").get.head.text
          if (appliedCheckstyleSeverityLevels.contains(severity)) {
            val lineNumber: String = error.attribute("line").get.head.text
            val filename: String = file.attribute("name").get.head.text
            val errorMessage: String = error.attribute("message").get.head.text
            log.error("Checkstyle " + severity + " found in " + filename + ":" + lineNumber + ": " + errorMessage)
            issuesFound += 1
          }
        }
      }

      if (issuesFound > 0) {
        log.error(issuesFound + " issue(s) found in Checkstyle report: " + outputFile + "")
        sys.exit(1)
      }
    }
  }

  private def checkstyleMain(configFile: String, outputFile: String, javaSource: String, xsltTransformations: Option[Set[XSLTSettings]]) = {
    val targetFolder = file(outputFile).getParentFile.getAbsolutePath
    java.nio.file.Files.createDirectories(java.nio.file.Paths.get(targetFolder))

    val checkstyleArgs = Array(
      "-c", configFile, // checkstyle configuration file
      "-f", "xml", // output format
      "-o", outputFile, // output file
      javaSource // location of Java source file
    )

    // Checkstyle calls System.exit which would exit SBT
    // Thus we wrap the call to it with a special security policy
    // that forbids exiting the JVM
    noExit {
      CheckstyleMain(checkstyleArgs: _*)
    }

    xsltTransformations match {
      case None => // Nothing to do
      case Some(xslt) => applyXSLT(file(outputFile), xslt)
    }
  }

  /**
    * Applies a set of XSLT transformation to the XML file produced by checkstyle
    *
    * @param input The XML file produced by checkstyle
    * @param transformations The XSLT transformations to be applied
    */
  private def applyXSLT(input: File, transformations: Set[XSLTSettings]): Unit = {
    val processor = new Processor(false)
    val source = processor.newDocumentBuilder().build(input)

    transformations foreach { transform: XSLTSettings =>
      val output = processor.newSerializer(transform.output)
      val compiler = processor.newXsltCompiler()
      val executor = compiler.compile(new StreamSource(transform.xslt))
      val transformer = executor.load()
      transformer.setInitialContextNode(source)
      transformer.setDestination(output)
      transformer.transform()
      transformer.close()
      output.close()
    }
  }

  /**
    * Wraps a block of code and executes it, preventing exits from the
    * JVM.  It does this by using a custom SecurityManager that throws
    * an exception if exitVM permission is checked.
    *
    * @param block The block of code to wrap and execute
    */
  def noExit(block: => Unit): Unit = {
    val original = System.getSecurityManager
    System.setSecurityManager(new NoExitSecurityManager())

    try {
      block
    } catch {
      case _: NoExitException =>
      case e: Throwable => throw e
    } finally {
      System.setSecurityManager(original)
    }
  }

  val checkstyleSettings: Seq[Def.Setting[_]] = Seq(
    checkstyleTarget <<= target(_ / "checkstyle-report.xml"),
    checkstyleTarget in Test <<= target(_ / "checkstyle-test-report.xml"),
    checkstyleConfig := file("checkstyle-config.xml"),
    checkstyleConfig in Test <<= checkstyleConfig,
    checkstyle in Compile <<= checkstyleTask(Compile),
    checkstyle in Test <<= checkstyleTask(Test),
    xsltTransformations := None,
    checkstyleCheckSeverityLevel := "error",
    checkstyleCheck in Compile <<= checkstyleCheckTask(Compile),
    checkstyleCheck in Test <<= checkstyleCheckTask(Test)
  )
}
