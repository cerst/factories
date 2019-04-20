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
