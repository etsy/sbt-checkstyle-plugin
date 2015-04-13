package com.etsy.sbt

import com.puppycrawl.tools.checkstyle.Main.{main => CheckstyleMain}
import sbt.Def.Initialize
import sbt.Keys._
import sbt._

/**
  * An SBT plugin to run checkstyle over Java code
  *  
  * @author Andrew Johnson <ajohnson@etsy.com>
  */
object Checkstyle extends Plugin {
  import com.etsy.sbt.Checkstyle.CheckstyleTasks._

  object CheckstyleTasks {
    val checkstyle = TaskKey[Unit]("checkstyle", "Runs checkstyle")
    val checkstyleTarget = SettingKey[File]("checkstyle-target", "The location of the generated checkstyle report")
    val checkstyleConfig = SettingKey[File]("checkstyle-config", "The location of the checkstyle configuration file")
  }

  /**
    * Runs checkstyle
    * 
    * @param conf The configuration (Compile or Test) in which context to execute the checkstyle command
    */
  def checkstyleTask(conf: Configuration): Initialize[Task[Unit]] = Def.task {
    val checkstyleArgs = Array(
      "-c", (checkstyleConfig in conf).value.getAbsolutePath, // checkstyle configuration file
      (javaSource in conf).value.getAbsolutePath, // location of Java source file
      "-f", "xml", // output format
      "-o", (checkstyleTarget in conf).value.getAbsolutePath // output file
    )
    // Checkstyle calls System.exit which would exit SBT
    // Thus we wrap the call to it with a special security policy
    // that forbids exiting the JVM
    noExit {
      CheckstyleMain(checkstyleArgs)
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
      case e : Throwable => throw e
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
    checkstyle in Test <<= checkstyleTask(Test)
  )
}
