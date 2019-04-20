package com.github.cerst.factories.util

import org.scalacheck.Shrink

/**
  * Mix-in which prevents shrinking to occur for all tests.
  */
trait NoShrink {

  implicit def noShrink[A]: Shrink[A] = Shrink[A](_ => Stream.empty)

}
