package com.github.cerst.factories

import com.github.cerst.factories.constraints.LongConstraints._
import com.github.cerst.factories.util.NumericConstraintsSpec

final class LongConstraintsSpec
    extends NumericConstraintsSpec[Long](dec = _ - 1, inc = _ + 1, globalMax = Long.MaxValue, globalMin = Long.MinValue) {}
