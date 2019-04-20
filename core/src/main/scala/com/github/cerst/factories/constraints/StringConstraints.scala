package com.github.cerst.factories.constraints

import com.github.cerst.factories.syntax._

trait StringConstraints {

  implicit val lengthLessThanForString: LengthLessThan[String] = x => { y =>
    if (y.lengthCompare(x) < 0) List.empty else List(s"_.length < $x")
  }

  implicit val lengthLessThanOrEqualForString: LengthLessThanOrEqual[String] = x => { y =>
    if (y.lengthCompare(x) <= 0) List.empty else List(s"_.length <= $x")
  }

  implicit val lengthGreaterThanForString: LengthGreaterThan[String] = x => { y =>
    if (y.lengthCompare(x) > 0) List.empty else List(s"_.length > $x")
  }

  implicit val lengthGreaterThanOrEqualForString: LengthGreaterThanOrEqual[String] = x => { y =>
    if (y.lengthCompare(x) >= 0) List.empty else List(s"_.length >= $x")
  }

  implicit val matchesForString: Matches[String] = x => { y =>
    if (x.pattern.matcher(y).matches()) List.empty else List(s"_ matches $x")
  }

}

object StringConstraints extends StringConstraints
