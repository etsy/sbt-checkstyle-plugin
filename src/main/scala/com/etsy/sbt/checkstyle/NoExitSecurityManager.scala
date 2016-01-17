package com.etsy.sbt.checkstyle

import java.security.Permission

/**
  * A SecurityManager that throws an Exception if the exitVM
  * permission is checked.
  *
  * @author Andrew Johnson
  */
class NoExitSecurityManager extends SecurityManager {
  /**
    * Throws a NoExitException if access is requested to the exitVM permission
    *
    * @param perm The requested permission
    */
  override def checkPermission(perm: Permission) {
    if (perm.getName.startsWith("exitVM")) throw NoExitException()
  }
}

/**
  * Custom SecurityException to indicate a VM exit was attempted
  *
  * @author Andrew Johnson
  */
case class NoExitException() extends SecurityException
