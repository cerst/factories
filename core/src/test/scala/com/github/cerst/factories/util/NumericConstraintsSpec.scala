package com.github.cerst.factories.util

import com.github.cerst.factories.syntax._
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

abstract class NumericConstraintsSpec[A: Numeric: Choose](dec: A => A, inc: A => A, globalMax: A, globalMin: A)(
  implicit greaterThanForA: GreaterThan[A],
  greaterThanOrEqualForA: GreaterThanOrEqual[A],
  lessThanForA: LessThan[A],
  lessThanOrEqualForA: LessThanOrEqual[A]
) extends FreeSpec
    with Matchers
    with GeneratorDrivenPropertyChecks
    with NoShrink {

  private val genBaseWithGreater: Gen[(A, A)] = {
    for {
      // decrement globalMax to ensure there is at least one greater value
      base <- Gen.chooseNum(globalMin, dec(globalMax))
      // increment base to ensure x > base
      larger <- Gen.chooseNum(inc(base), globalMax)
    } yield {
      (base, larger)
    }
  }

  private val genBaseWithGreaterOrEqual: Gen[(A, A)] = {
    for {
      base <- Gen.chooseNum(globalMin, globalMax)
      lessThanOrEqual <- Gen.chooseNum(base, globalMax)
    } yield {
      (base, lessThanOrEqual)
    }
  }

  private val genBaseWithLesser: Gen[(A, A)] = {
    for {
      // increment globalMin to ensure that there is at least one lesser value
      base <- Gen.chooseNum(inc(globalMin), globalMax)
      // decrement base to ensure x < base
      smaller <- Gen.chooseNum(globalMin, dec(base))
    } yield {
      (base, smaller)
    }
  }

  private val genBaseWithLesserOrEqual: Gen[(A, A)] = {
    for {
      base <- Gen.chooseNum(globalMin, globalMax)
      lessThanOrEqual <- Gen.chooseNum(globalMin, base)
    } yield {
      (base, lessThanOrEqual)
    }
  }

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
