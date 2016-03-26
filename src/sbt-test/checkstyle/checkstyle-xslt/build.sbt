version := "0.1"

name := "checkstyle-xslt"

organization := "com.etsy"

checkstyleXsltTransformations := {
  Some(Set(CheckstyleXSLTSettings(baseDirectory(_ / "checkstyle-noframes.xml").value, target(_ / "checkstyle-report.html").value)))
}
