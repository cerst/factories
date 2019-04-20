package com.github.cerst.factories

import com.github.cerst.factories.constraints.ShortConstraints._
import com.github.cerst.factories.util.NumericConstraintsSpec

final class ShortConstraintsSpec
    extends NumericConstraintsSpec[Short](
      dec = x => (x - 1).toShort,
      inc = x => (x + 1).toShort,
      globalMax = Short.MaxValue,
      globalMin = Short.MinValue
    ) {}
