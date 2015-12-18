version := "0.1"

name := "xslt"

organization := "com.etsy"

import com.etsy.sbt.checkstyle._

Checkstyle.checkstyleSettings
Checkstyle.xsltTransformations := {
  Some(Set(XSLTSettings(baseDirectory(_ / "checkstyle-noframes.xml").value, target(_ / "checkstyle-report.html").value)))
}
