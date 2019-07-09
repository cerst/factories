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

package usage

// #numeric_example
import com.github.cerst.factories.DefaultConstraints._
import com.github.cerst.factories._

import scala.util.Try

object NumericExample {

  final case class PersonId(intValue: Int) extends AnyVal

  object PersonId {

    @throws[IllegalArgumentException]
    def apply(intValue: Int): PersonId = {
      // constraints are
      // - passed-in as varargs (i.e. you can specify 0 or more)
      // - evaluated in an '&&' fashion (i.e. the result is an error if and only if at least one constraint is violated)
      intValue.create(new PersonId(_), _ >= 0, _ <= 1024)
    }

    // there's also an overload to represent the error case using Either
    def apply2(intValue: Int): Either[String, PersonId] = {
      intValue.createEither(new PersonId(_), _ >= 0, _ <= 1024)
    }

    // and an overload to represent the error case using Try
    def apply3(intValue: Int): Try[PersonId] = {
      intValue.createTry(new PersonId(_), _ >= 0, _ <= 1024)
    }

  }

  def main(args: Array[String]): Unit = {
    println(PersonId.apply3(-1))
    // Failure(java.lang.IllegalArgumentException: '-1' is not a valid 'PersonId' due to the following constraint violations: [ _ >= 0 ])

    println(PersonId.apply2(-1))
    // Left('-1' is not a valid 'PersonId' due to the following constraint violations: [ _ >= 0 ])

    println(PersonId(-1))
    // Exception in thread "main" java.lang.IllegalArgumentException: '-1' is not a valid 'PersonId' due to the following constraint violations: [ _ >= 0 ]
  }

}

// #numeric_example
