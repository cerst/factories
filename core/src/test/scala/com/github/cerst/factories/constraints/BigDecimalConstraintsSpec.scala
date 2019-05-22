package com.github.cerst.factories.constraints

import com.github.cerst.factories.constraints.BigDecimalConstraints._
import com.github.cerst.factories.constraints.BigDecimalConstraintsSpec.chooseForBigDecimal
import com.github.cerst.factories.util.NumericConstraintsSpec
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose

final class BigDecimalConstraintsSpec
    extends NumericConstraintsSpec[BigDecimal](
      dec = x => BigDecimal(Math nextDown x.doubleValue()),
      inc = x => BigDecimal(Math nextUp x.doubleValue()),
      globalMax = BigDecimal(Double.MaxValue),
      globalMin = BigDecimal(Double.MinValue)
    ) {}

object BigDecimalConstraintsSpec {

  implicit final val chooseForBigDecimal: Choose[BigDecimal] = {
    Gen.Choose.xmap[Double, BigDecimal](BigDecimal.apply, _.toDouble)
  }

}
