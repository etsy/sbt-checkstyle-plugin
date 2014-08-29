package com.etsy.sbt

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.junit.Rule
import com.etsy.sbt.Checkstyle._
import scala.annotation.meta.getter
import org.junit.contrib.java.lang.system.ExpectedSystemExit

class CheckstyleSuite extends JUnitSuite {
  @(Rule @getter)
  def exitRule = ExpectedSystemExit.none()

  @Test
  def testTrapExits() = {
    val originalSecManager = System.getSecurityManager
    trapExits {
      sys.exit(1)
    }
    assertEquals("Security manager changed after execution", originalSecManager, System.getSecurityManager)
  }
}
