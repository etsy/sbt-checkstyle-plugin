package com.etsy.sbt

import java.security.Permission

/**
  * A SecurityManager that throws an Exception if the exitVM
  * permission is checked.
  * 
  * @author Andrew Johnson
  */
class NoExitSecurityManager extends SecurityManager {
  override def checkPermission(perm: Permission) {
    if (perm.getName startsWith "exitVM") throw NoExitException()
  }
}

/**
  * Custom SecurityException to indicate a VM exit was attempted
  *
  * @author Andrew Johnson
  */
case class NoExitException() extends SecurityException
