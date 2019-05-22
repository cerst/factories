package com.github.cerst.factories.constraints

import java.time.Instant

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait InstantConstraints {

  implicit final val lessThanForInstant: LessThan[Instant] = x => { y =>
    if (y.compareTo(x) < 0) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForInstant: LessThanOrEqual[Instant] = x => { y =>
    if (y.compareTo(x) <= 0) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForInstant: GreaterThan[Instant] = x => { y =>
    if (y.compareTo(x) > 0) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForInstant: GreaterThanOrEqual[Instant] = x => { y =>
    if (y.compareTo(x) >= 0) List.empty else List(s"_ >= $x")
  }

}

object InstantConstraints extends InstantConstraints
