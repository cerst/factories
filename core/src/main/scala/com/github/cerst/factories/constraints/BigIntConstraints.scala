package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait BigIntConstraints {

  implicit final val lessThanForBigInt: LessThan[BigInt] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForBigInt: LessThanOrEqual[BigInt] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForBigInt: GreaterThan[BigInt] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForBigInt: GreaterThanOrEqual[BigInt] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object BigIntConstraints extends BigIntConstraints
