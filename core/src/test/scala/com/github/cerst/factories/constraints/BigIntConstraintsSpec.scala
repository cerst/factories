/*
 * Copyright (c) 2019 Constantin Gerstberger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
