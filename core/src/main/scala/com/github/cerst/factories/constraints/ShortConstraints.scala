package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait ShortConstraints {

  implicit final val lessThanForShort: LessThan[Short] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForShort: LessThanOrEqual[Short] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForShort: GreaterThan[Short] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForShort: GreaterThanOrEqual[Short] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object ShortConstraints extends ShortConstraints
