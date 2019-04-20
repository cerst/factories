package com.github.cerst.factories

import com.github.cerst.factories.util.NoShrink
import org.scalacheck.Gen
import org.scalatest.prop.{GeneratorDrivenPropertyChecks, TableDrivenPropertyChecks}
import org.scalatest.{FreeSpec, Matchers}
import com.github.cerst.factories.constraints.StringConstraints._
import com.github.cerst.factories.syntax.ConstraintSyntax

final class StringConstraintsSpec
    extends FreeSpec
    with Matchers
    with GeneratorDrivenPropertyChecks
    with TableDrivenPropertyChecks
    with NoShrink {

  private val genLengthWithLonger: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize - 1)
      yLength <- Gen.chooseNum(x + 1, StringConstraintsSpec.MaxStringSize)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithLongerOrEqual: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(x, StringConstraintsSpec.MaxStringSize)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithShorter: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(1, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(0, x - 1)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithShorterOrEqual: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(0, x)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  // ===================================================================================================================
  // _.length <
  // ===================================================================================================================
  "'_.length < x' does not succeed with 'y.length >= x'" in {
    forAll(genLengthWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<(x).apply(y)
        val expected = List(s"_.length < $x")

        assert(actual == expected)
    }
  }

  "'_.length < x' succeeds with 'y.length < x'" in {
    forAll(genLengthWithShorter) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length <=
  // ===================================================================================================================
  "'_.length <= x' does not succeed with 'y.length > x'" in {
    forAll(genLengthWithLonger) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<=(x).apply(y)
        val expected = List(s"_.length <= $x")

        assert(actual == expected)
    }
  }

  "'_.length <= x' succeeds with 'y.length <= x'" in {
    forAll(genLengthWithShorterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length >
  // ===================================================================================================================
  "'_.length > x' does not succeed with 'y.length <= x'" in {
    forAll(genLengthWithShorterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>(x).apply(y)
        val expected = List(s"_.length > $x")

        assert(actual == expected)
    }
  }

  "'_.length > x' succeeds with 'y.length > x'" in {
    forAll(genLengthWithLonger) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length >=
  // ===================================================================================================================
  "'_.length >= x' does not succeed with 'y.length < x'" in {
    forAll(genLengthWithShorter) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>=(x).apply(y)
        val expected = List(s"_.length >= $x")

        assert(actual == expected)
    }
  }

  "'_.length >= x' succeeds with 'y.length >= x'" in {
    forAll(genLengthWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // matches
  // ===================================================================================================================
  val regexMatchFalseTrue =
    Table(("regex", "NOT matched", "matched"), ("""\d{1,3}\.\d{1,3}\.\d{1,3}""".r, "1234", "192.168.0.1"))

  "'_ matches x' does not succeed with y !matches x" in {

    forAll(regexMatchFalseTrue) {
      // 'testRegex' to avoid naming collision with Scalatest DSL term 'regex'
      case (testRegex, notMatched, _) =>
        val actual = ConstraintSyntax.matches(testRegex).apply(notMatched)
        val expected = List(s"_ matches $testRegex")

        assert(actual == expected)
    }
  }

  "'_ matches x' succeeds with y matches x" in {
    forAll(regexMatchFalseTrue) {
      // 'testRegex' to avoid naming collision with Scalatest DSL term 'regex'
      case (testRegex, _, matched) =>
        val actual = ConstraintSyntax.matches(testRegex).apply(matched)
        val expected = List(s"_ matches $testRegex")

        assert(actual == expected)
    }
  }

}

object StringConstraintsSpec {

  val MaxStringSize = 1024

}
