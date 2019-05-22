package com.github.cerst.factories.constraints

import java.time.Duration

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait JavaDurationConstraints {

  implicit final val lessThanForJavaDuration: LessThan[Duration] = x => { y =>
    if (y.compareTo(x) < 0) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualJavaDuration: LessThanOrEqual[Duration] = x => { y =>
    if (y.compareTo(x) <= 0) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForJavaDuration: GreaterThan[Duration] = x => { y =>
    if (y.compareTo(x) > 0) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualJavaDuration: GreaterThanOrEqual[Duration] = x => { y =>
    if (y.compareTo(x) >= 0) List.empty else List(s"_ >= $x")
  }

}

object JavaDurationConstraints extends JavaDurationConstraints
