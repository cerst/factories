package com.github.cerst.factories

import com.github.cerst.factories.constraints.DoubleConstraints._
import com.github.cerst.factories.util.NumericConstraintsSpec

final class DoubleConstraintsSpec
    extends NumericConstraintsSpec[Double](
      dec = Math.nextDown,
      inc = Math.nextUp,
      globalMax = Double.MaxValue,
      globalMin = Double.MinValue,
    ) {}
