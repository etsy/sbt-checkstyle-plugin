version := "0.1"

name := "xslt"

organization := "com.etsy"

import com.etsy.sbt.Checkstyle
import com.etsy.sbt.XSLTSettings

Checkstyle.checkstyleSettings
Checkstyle.CheckstyleTasks.xsltTransformations := {
  Some(Set(XSLTSettings(baseDirectory(_ / "checkstyle-noframes.xml").value, target(_ / "checkstyle-report.html").value)))
}
