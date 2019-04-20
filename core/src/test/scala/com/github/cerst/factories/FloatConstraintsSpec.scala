package com.github.cerst.factories

import com.github.cerst.factories.util.NumericConstraintsSpec
import com.github.cerst.factories.constraints.FloatConstraints._

final class FloatConstraintsSpec
    extends NumericConstraintsSpec[Float](
      dec = Math.nextDown,
      inc = Math.nextUp,
      globalMax = Float.MaxValue,
      globalMin = Float.MinValue
    ) {}
