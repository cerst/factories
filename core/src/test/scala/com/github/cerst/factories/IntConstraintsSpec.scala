package com.github.cerst.factories

import com.github.cerst.factories.constraints.IntConstraints._
import com.github.cerst.factories.util.NumericConstraintsSpec

final class IntConstraintsSpec
    extends NumericConstraintsSpec[Int](dec = _ - 1, inc = _ + 1, globalMax = Int.MaxValue, globalMin = Int.MinValue)
