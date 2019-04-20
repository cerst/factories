package com.github.cerst.factories

import com.github.cerst.factories.constraints.{
  DoubleConstraints,
  FloatConstraints,
  IntConstraints,
  LongConstraints,
  ShortConstraints,
  StringConstraints
}

object DefaultConstraints
    extends DoubleConstraints
    with FloatConstraints
    with IntConstraints
    with LongConstraints
    with ShortConstraints
    with StringConstraints
