package com.etsy.sbt.checkstyle

import sbt.File

import scala.io.Source

sealed abstract class CheckstyleConfig(val location: String) {
    def read(resources: Seq[File]): String
  }

  object CheckstyleConfig {
    case class URL(url: String) extends CheckstyleConfig(url) {
      override def read(resources: Seq[sbt.File]): String = Source.fromURL(url).mkString
    }

    case class File(path: String) extends CheckstyleConfig(path) {
      override def read(resources: Seq[sbt.File]): String = Source.fromFile(path).mkString
    }

    case class Classpath(name: String) extends CheckstyleConfig(name) {
      override def read(resources: Seq[sbt.File]): String = {
        val classpath = resources.map((f) => f.toURI.toURL)
        val loader = new java.net.URLClassLoader(classpath.toArray, getClass.getClassLoader)
        Source.fromInputStream(loader.getResourceAsStream(name)).mkString
      }
    }
  }
