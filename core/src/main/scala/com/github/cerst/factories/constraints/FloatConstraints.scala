package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait FloatConstraints {

  implicit final val lessThanForFloat: LessThan[Float] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForFloat: LessThanOrEqual[Float] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForFloat: GreaterThan[Float] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForFloat: GreaterThanOrEqual[Float] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object FloatConstraints extends FloatConstraints
