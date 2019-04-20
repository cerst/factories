package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait DoubleConstraints {

  implicit final val lessThanForDouble: LessThan[Double] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForDouble: LessThanOrEqual[Double] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForDouble: GreaterThan[Double] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForDouble: GreaterThanOrEqual[Double] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object DoubleConstraints extends DoubleConstraints
