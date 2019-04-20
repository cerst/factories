package com.github.cerst.factories.syntax

import com.github.cerst.factories.Constraint

import scala.annotation.implicitNotFound
import scala.util.matching.Regex

@implicitNotFound("No 'LengthLessThan' (_length < x) implementation found for ${A}")
trait LengthLessThan[A] extends (Int => Constraint[A])

@implicitNotFound("No 'LengthLessThanOrEqual' (_length <= x) implementation found for ${A}")
trait LengthLessThanOrEqual[A] extends (Int => Constraint[A])

@implicitNotFound("No 'LengthGreaterThan' (_length > x) implementation found for ${A}")
trait LengthGreaterThan[A] extends (Int => Constraint[A])

@implicitNotFound("No 'LengthGreaterThanOrEqual' (_length >= x) implementation found for ${A}")
trait LengthGreaterThanOrEqual[A] extends (Int => Constraint[A])

@implicitNotFound("No 'LessThan' (_ < x) implementation found for ${A}")
trait LessThan[A] extends (A => Constraint[A])

@implicitNotFound("No 'LessThanOrEqual' (_ <= x) implementation found for ${A}")
trait LessThanOrEqual[A] extends (A => Constraint[A])

@implicitNotFound("No 'GreaterThan' (_ > x) implementation found for ${A}")
trait GreaterThan[A] extends (A => Constraint[A])

@implicitNotFound("No 'GreaterThanOrEqual' (_ >= x) implementation found for ${A}")
trait GreaterThanOrEqual[A] extends (A => Constraint[A])

@implicitNotFound("No 'Matches' (_ matches x) implementation found for ${A}")
trait Matches[A] extends (Regex => Constraint[A])

final object ConstraintSyntax {

  final object length {

    def <[A](x: Int)(implicit ev: LengthLessThan[A]): Constraint[A] = ev(x)

    def <=[A](x: Int)(implicit ev: LengthLessThanOrEqual[A]): Constraint[A] = ev(x)

    def >[A](x: Int)(implicit ev: LengthGreaterThan[A]): Constraint[A] = ev(x)

    def >=[A](x: Int)(implicit ev: LengthGreaterThanOrEqual[A]): Constraint[A] = ev(x)

  }

  def <[A](x: A)(implicit ev: LessThan[A]): Constraint[A] = ev(x)

  def <=[A](x: A)(implicit ev: LessThanOrEqual[A]): Constraint[A] = ev(x)

  def >[A](x: A)(implicit ev: GreaterThan[A]): Constraint[A] = ev(x)

  def >=[A](x: A)(implicit ev: GreaterThanOrEqual[A]): Constraint[A] = ev(x)

  def matches[A](x: Regex)(implicit ev: Matches[A]): Constraint[A] = ev(x)

}
