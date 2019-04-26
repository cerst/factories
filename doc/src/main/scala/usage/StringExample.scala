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

// #string_example
import com.github.cerst.factories.DefaultConstraints._
import com.github.cerst.factories._

import scala.util.matching.Regex

object StringExample {

  final case class IpV4Address(stringValue: String) extends AnyVal

  object IpV4Address {

    val regex: Regex = """\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}""".r

    def apply(string: String): IpV4Address = {
      // constraints are
      // - passed-in as varargs (i.e. you can specify 0 or more)
      // - evaluated in an '&&' fashion (i.e. the result is an error if and only if at least one constraint is violated)
      string.create(new IpV4Address(_), _.length >= 0, _ matches regex)
    }

    // there's also on overload to represent the error case as part of the return type
    def apply2(string: String): Either[String, IpV4Address] = {
      string.createEither(new IpV4Address(_), _ matches regex)
    }

  }

  def main(args: Array[String]): Unit = {
    println(IpV4Address.apply2("1234"))
    // Left('1234' is not a valid 'IpV4Address' due to the following constraint violations: [ _ matches \d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3} ])

    println(IpV4Address.apply("1234"))
    // Exception in thread "main" java.lang.IllegalArgumentException: '1234' is not a valid 'IpV4Address' due to the following constraint violations: [ _ matches \d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3} ]
  }

}
// #string_example
