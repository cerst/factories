package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait IntConstraints {

  implicit final val lessThanForInt: LessThan[Int] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForInt: LessThanOrEqual[Int] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForInt: GreaterThan[Int] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForInt: GreaterThanOrEqual[Int] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object IntConstraints extends IntConstraints
