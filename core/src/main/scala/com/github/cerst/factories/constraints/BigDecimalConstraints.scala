package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait BigDecimalConstraints {

  implicit final val lessThanForBigDecimal: LessThan[BigDecimal] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForBigDecimal: LessThanOrEqual[BigDecimal] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForBigDecimal: GreaterThan[BigDecimal] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForBigDecimal: GreaterThanOrEqual[BigDecimal] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object BigDecimalConstraints extends BigDecimalConstraints
