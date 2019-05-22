package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

import scala.concurrent.duration.Duration

trait DurationConstraints {

  implicit final val lessThanForDuration: LessThan[Duration] = x => { y =>
    if (y < x) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualDuration: LessThanOrEqual[Duration] = x => { y =>
    if (y <= x) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForDuration: GreaterThan[Duration] = x => { y =>
    if (y > x) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualDuration: GreaterThanOrEqual[Duration] = x => { y =>
    if (y >= x) List.empty else List(s"_ >= $x")
  }

}

object DurationConstraints extends DurationConstraints
