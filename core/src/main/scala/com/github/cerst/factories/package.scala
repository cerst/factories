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
import scala.util.{Failure, Success, Try}

package object factories {

  final type Constraint[A] = A => List[String]

  /**
    * Decorator class which contains the methods for implementing factory methods.
    *
    * @param a The input value for the factory method against which constraints are evaluated beforehand.
    */
  implicit final class Ops[A](val a: A) extends AnyVal {

    private def checkConstraints[B: ClassTag](
      constraints: Seq[ConstraintSyntax.type => Constraint[A]]
    ): Option[String] = {
      val violations = constraints.flatMap(f => f(ConstraintSyntax)(a))
      if (violations.nonEmpty) {
        def bName = implicitly[ClassTag[B]].runtimeClass.getSimpleName
        def formattedViolations = violations.mkString("[ ", ", ", " ]")
        def message = s"'$a' is not a valid '$bName' due to the following constraint violations: $formattedViolations"
        Some(message)
      } else {
        None
      }
    }

    /**
      * Invokes the provided <i>create</i> function if and only if all provided <i>constraints</i> are met for the
      * value this method is called on.
      *
      * @param create      The "actual/inner factory method" which usually cannot enforce the given constraints
      *                    (e.g. value class constructor).<br/>
      *                    Invoked <b>without</b> any guards against exceptions, blocking etc.
      * @param constraints The constraints to enforce for the value this method is called on.
      * @tparam B          Type of the value to be created by the factory method (the
      *                    [[scala.reflect.ClassTag ClassTag]] is used to display its name in error messages).
      * @return
      */
    @throws[IllegalArgumentException]("If and only if at least one of the provided <i>constraints<i> is violated")
    def create[B: ClassTag](create: A => B, constraints: (ConstraintSyntax.type => Constraint[A])*): B = {
      checkConstraints(constraints) match {
        case None =>
          create(a)
        case Some(value) =>
          throw new IllegalArgumentException(value)
      }
    }

    // Scaladoc note: I couldn't get scala.util.Left (/Right) to link properly (i.e. with sbt doc working)
    /**
      * Invokes the provided <i>create</i> function if and only if all provided <i>constraints</i> are met for the
      * value this method is called on.
      *
      * @param create      The "actual/inner factory method" which usually cannot enforce the given constraints
      *                    (e.g. value class constructor).<br/>
      *                    Invoked <b>without</b> any guards against exceptions, blocking etc.
      * @param constraints The constraints to enforce for the value this method is called on.
      * @tparam B          Type of the value to be created by the factory method (the
      *                    [[scala.reflect.ClassTag ClassTag]] is used to display its name in error messages).
      * @return            An error message as <i>Left</i> if and only if at least one of the provided
      *                    <i>constraints</i> is violated. The result of invoking the provided <i>create</i> function
      *                    as <i>Right</i> otherwise.
      */
    def createEither[B: ClassTag](create: A => B,
                                  constraints: (ConstraintSyntax.type => Constraint[A])*): Either[String, B] = {
      checkConstraints(constraints) match {
        case None =>
          Right(create(a))
        case Some(value) =>
          Left(value)
      }
    }

    // Scaladoc note: I couldn't get scala.util.Failure (/Success) to link properly (i.e. with sbt doc working)
    /**
      * Invokes the provided <i>create</i> function if and only if all provided <i>constraints</i> are met for the
      * value this method is called on.
      *
      * @param create      The "actual/inner factory method" which usually cannot enforce the given constraints
      *                    (e.g. value class constructor).<br/>
      *                    Invoked <b>without</b> any guards against exceptions, blocking etc.
      * @param constraints The constraints to enforce for the value this method is called on.
      * @tparam B          Type of the value to be created by the factory method (the
      *                    [[scala.reflect.ClassTag ClassTag]] is used to display its name in error messages).
      * @return            An error message as <i>Failure</i> [ [[scala#IllegalArgumentException]] ]
      *                    if and only if at least one of the provided <i>constraints</i> is violated. The result of
      *                    invoking the provided <i>create</i> function as <i>Success</i> otherwise.
      */
    def createTry[B: ClassTag](create: A => B, constraints: (ConstraintSyntax.type => Constraint[A])*): Try[B] = {
      checkConstraints(constraints) match {
        case None =>
          Success(create(a))
        case Some(value) =>
          Failure(new IllegalArgumentException(value))
      }
    }

  }

}
