package com.github.cerst.factories.constraints

import com.github.cerst.factories.constraints.BigIntConstraints._
import com.github.cerst.factories.constraints.BigIntConstraintsSpec.chooseForBigInt
import com.github.cerst.factories.util.NumericConstraintsSpec
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose

// Generate values from the range delimited by long should be enough though BigInt's exclusive max is 2^Int.MaxValue
final class BigIntConstraintsSpec
    extends NumericConstraintsSpec[BigInt](
      dec = _ - 1,
      inc = _ + 1,
      globalMax = BigInt(Long.MaxValue),
      globalMin = BigInt(Long.MinValue)
    ) {}

object BigIntConstraintsSpec {

  implicit final val chooseForBigInt: Choose[BigInt] = Gen.Choose.xmap[Long, BigInt](BigInt.apply, _.toLong)

}
