version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.configLocation := CheckstyleConfig.File("checkstyle-config.xml")
