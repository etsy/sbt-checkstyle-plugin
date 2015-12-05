import com.etsy.sbt.Checkstyle.CheckstyleTasks._
import com.etsy.sbt.XSLTSettings

com.etsy.sbt.Checkstyle.checkstyleSettings

version := "0.1"

name := "checkstyle-test"

organization := "com.etsy"

xsltTransformations := {
  Some(Set(XSLTSettings(baseDirectory(_ / "checkstyle-noframes.xml").value, target(_ / "checkstyle-report.html").value)))
}
