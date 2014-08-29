package com.etsy.sbt

import sbt._
import sbt.Keys._
import sbt.std.TaskStreams
import Def.{Initialize}
import com.puppycrawl.tools.checkstyle.Main.{ main => CsMain }

// This plugin enables running checkstyle on Java sources
object Checkstyle extends Plugin {
  import CheckstyleTasks._

  object CheckstyleTasks {
    lazy val checkstyle = TaskKey[Unit]("checkstyle")

    lazy val checkstyleTarget = SettingKey[File]("checkstyle-target")
    lazy val checkstyleConfig = SettingKey[File]("checkstyle-config")
  }

  def genericCheckstyleTask(conf: Configuration) = {
    Def.task {
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
        CsMain(args.toArray)
      }
    }
  }

  def trapExits(thunk: => Unit): Unit = {
    val originalSecManager = System.getSecurityManager
    case class NoExitsException() extends SecurityException
    System setSecurityManager new SecurityManager() {
      import java.security.Permission
      override def checkPermission(perm: Permission) {
        if (perm.getName startsWith "exitVM") throw NoExitsException()
      }
    }
    try {
      thunk
    } catch {
      case _: NoExitsException =>
      case e : Throwable => throw e
    } finally {
      System setSecurityManager originalSecManager
    }
  }

  def checkstyleTask : Initialize[Task[Unit]] = {
    genericCheckstyleTask(Compile)
  }

  def checkstyleTestTask : Initialize[Task[Unit]] = {
    genericCheckstyleTask(Test)
  }

  val checkstyleSettings = Seq(
    checkstyleTarget <<= target(_ / "checkstyle-report.xml"),
    checkstyleConfig := file("checkstyle-checkstyleConfig.xml"),
    checkstyle <<= checkstyleTask,
    checkstyle in Test <<= checkstyleTestTask
  )
}
