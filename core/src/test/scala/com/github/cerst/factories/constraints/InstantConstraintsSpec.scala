package com.github.cerst.factories.constraints

import java.time.Instant

import com.github.cerst.factories.constraints.InstantConstraints._
import com.github.cerst.factories.syntax.ConstraintSyntax
import com.github.cerst.factories.util.NoShrink
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

final class InstantConstraintsSpec extends FreeSpec with Matchers with GeneratorDrivenPropertyChecks with NoShrink {

  import InstantConstraintsSpec._

  // ===================================================================================================================
  // <
  // ===================================================================================================================
  "'_ < x' does not succeed with y >= x" in {
    forAll(genBaseWithGreaterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.<(x).apply(y)
        val expected = List(s"_ < $x")

        assert(actual == expected)
    }
  }

  "'_ < x' succeeds with y < x" in {
    forAll(genBaseWithLesser) {
      case (x, y) =>
        val actual = ConstraintSyntax.<(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // <=
  // ===================================================================================================================
  "'_ <= x' does not succeed with y > x" in {
    forAll(genBaseWithGreater) {
      case (x, y) =>
        val actual = ConstraintSyntax.<=(x).apply(y)
        val expected = List(s"_ <= $x")

        assert(actual == expected)
    }
  }

  "'_ <= x' succeeds with y <= x" in {
    forAll(genBaseWithLesserOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.<=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // >
  // ===================================================================================================================
  "'_ > x' does not succeed with y <= x" in {
    forAll(genBaseWithLesserOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>(x).apply(y)
        val expected = List(s"_ > $x")

        assert(actual == expected)
    }
  }

  "'_ > x' succeeds with y > x" in {
    forAll(genBaseWithGreater) {
      case (x, y) =>
        val actual = ConstraintSyntax.>(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // >=
  // ===================================================================================================================
  "'_ >= x' does not succeed with y < x" in {
    forAll(genBaseWithLesser) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List(s"_ >= $x")

        assert(actual == expected)
    }
  }

  "'_ >= x' succeeds with y >= x" in {
    forAll(genBaseWithGreaterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

}

object InstantConstraintsSpec {

  private final val MinSeconds: Long = Instant.MIN.getEpochSecond
  private final val MaxSeconds: Long = Instant.MAX.getEpochSecond

  private final val MinNanos = 0L

  /** Max nanos of a second: 999.999.999 */
  private final val MaxNanos: Long = 999999999L

  private final val genBaseWithLesser: Gen[(Instant, Instant)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y < x', i,e. Gen(x).min == 1 AND
      //   generate y accordingly, i.e. Gen(y).max == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(if (xSeconds == ySeconds) MinNanos + 1 else MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(0L, if (xSeconds == ySeconds) xNanos - 1 else MaxNanos)
    } yield {
      val x = Instant.ofEpochSecond(xSeconds, xNanos)
      val y = Instant.ofEpochSecond(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithLesserOrEqual: Gen[(Instant, Instant)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y <= x', i,e. Gen(y).max == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(MinNanos, if (xSeconds == ySeconds) xNanos else MaxNanos)
    } yield {
      val x = Instant.ofEpochSecond(xSeconds, xNanos)
      val y = Instant.ofEpochSecond(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithGreater: Gen[(Instant, Instant)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y > x', i,e. Gen(x).max == MaxNanos -1 AND
      //   generate y accordingly, i.e. Gen(y).min == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(MinNanos, if (xSeconds == ySeconds) MaxNanos - 1 else MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos + 1 else MinNanos, MaxNanos)
    } yield {
      val x = Instant.ofEpochSecond(xSeconds, xNanos)
      val y = Instant.ofEpochSecond(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithGreaterOrEqual: Gen[(Instant, Instant)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y >= x', i,e. Gen(y).min == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos else MinNanos, MaxNanos)
    } yield {
      val x = Instant.ofEpochSecond(xSeconds, xNanos)
      val y = Instant.ofEpochSecond(ySeconds, yNanos)
      (x, y)
    }
  }

}