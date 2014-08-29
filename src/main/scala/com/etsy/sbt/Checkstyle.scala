package com.etsy.sbt

import sbt._
import sbt.Keys._
import sbt.std.TaskStreams
import Def.{Initialize}
import com.puppycrawl.tools.checkstyle.Main.{ main => CheckstyleMain }
import com.etsy.sbt._

/**
  * An SBT plugin to run checkstyle over Java code
  *  
  * @author Andrew Johnson <ajohnson@etsy.com>
  */
object Checkstyle extends Plugin {
  import CheckstyleTasks._

  object CheckstyleTasks {
    val checkstyle = TaskKey[Unit]("checkstyle")
    val checkstyleTarget = SettingKey[File]("checkstyle-target")
    val checkstyleConfig = SettingKey[File]("checkstyle-config")
  }

  def checkstyleTask(conf: Configuration) = Def.task {
    val args = List(
      "-c", checkstyleConfig.value.getAbsolutePath,
      "-f", "xml",
      "-r", (javaSource in conf).value.getAbsolutePath,
      "-o", checkstyleTarget.value.getAbsolutePath
    )
    // Checkstyle calls System.exit which would exit SBT
    // Thus we wrap the call to it with a special security policy
    // that forbids exiting the JVM
    trapExits {
      CheckstyleMain(args.toArray)
    }
  }

  /**
    * Wraps a block of code and executes it, preventing exits from the
    * JVM.  It does this by using a custom SecurityManager that throws
    * an exception if exitVM permission is checked.
    * 
    * @param block The block of code to wrap and execute
    */
  def trapExits(block: => Unit): Unit = {
    val original = System.getSecurityManager
    System setSecurityManager new NoExitSecurityManager()

    try {
      block
    } catch {
      case _: NoExitException =>
      case e : Throwable => throw e
    } finally {
      System setSecurityManager original
    }
  }

  val checkstyleSettings = Seq(
    checkstyleTarget <<= target(_ / "checkstyle-report.xml"),
    checkstyleConfig := file("checkstyle-config.xml"),
    checkstyle in Compile <<= checkstyleTask(Compile),
    checkstyle in Test <<= checkstyleTask(Test)
  )
}
