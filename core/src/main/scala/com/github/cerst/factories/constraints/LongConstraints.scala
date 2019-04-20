package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait LongConstraints {

  implicit final val lessThanForLong: LessThan[Long] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForLong: LessThanOrEqual[Long] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForLong: GreaterThan[Long] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForLong: GreaterThanOrEqual[Long] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object LongConstraints extends LongConstraints
