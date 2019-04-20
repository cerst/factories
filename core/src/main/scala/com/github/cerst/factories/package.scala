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

package com.github.cerst

import com.github.cerst.factories.syntax.ConstraintSyntax

import scala.reflect.ClassTag

package object factories {

  final type Constraint[A] = A => List[String]

  implicit final class Ops[A](val a: A) extends AnyVal {

    private def checkConstraints[B: ClassTag](
      constraints: Seq[ConstraintSyntax.type => Constraint[A]]
    ): Option[String] = {
      val violations = constraints.flatMap(f => f(ConstraintSyntax)(a))
      if (violations.nonEmpty) {
        def bName = implicitly[ClassTag[B]].runtimeClass.getSimpleName
        def formattedViolations = violations.mkString("[ ", ", ", " ]")
        def message =
          s"'$a' is not a valid $bName due to the following constraint violations: $formattedViolations"
        Some(message)
      } else {
        None
      }
    }

    def safeCreate[B: ClassTag](create: A => B,
                                constraints: (ConstraintSyntax.type => Constraint[A])*): Either[String, B] = {
      checkConstraints(constraints) match {
        case None =>
          Right(create(a))
        case Some(value) =>
          Left(value)
      }
    }

    @throws[IllegalArgumentException]("If at least one of the constraints is violated")
    def unsafeCreate[B: ClassTag](create: A => B, constraints: (ConstraintSyntax.type => Constraint[A])*): B = {
      checkConstraints(constraints) match {
        case None =>
          create(a)
        case Some(value) =>
          throw new IllegalArgumentException(value)
      }
    }

  }

}
