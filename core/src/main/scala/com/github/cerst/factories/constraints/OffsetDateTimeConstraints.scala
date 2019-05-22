package com.github.cerst.factories.constraints

import java.time.OffsetDateTime

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

/**
  * Implementation Note:
  * <p>
  * All constraints implement an instant-based comparison based on [[java.time.OffsetDateTime#isAfter isAfter]],
  * [[java.time.OffsetDateTime#isBefore isBefore]] or [[java.time.OffsetDateTime#isEqual isEqual]]. This is because:
  * <ul>
  * <li>[[java.time.OffsetDateTime#compareTo compareTo]] has different semantics</i>
  * <li>there is no <i>isBeforeOrSame</i> or <i>isAfterOrSame</i> for [[java.time.OffsetDateTime OffsetDateTime]]</li>
  * <li>(re-) implementation is more efficient that calling e.g. [[java.time.OffsetDateTime#isBefore isBefore]] and [[java.time.OffsetDateTime#isEqual isEqual]] in sequence</li>
  * <li>using the same implementation for all constraints instead of e.g. using [[java.time.OffsetDateTime#isBefore isBefore]] for implementing [[com.github.cerst.factories.syntax.LessThan]] increases readability</li>
  * <ul>
  */
trait OffsetDateTimeConstraints {

  private final def nanos(offsetDateTime: OffsetDateTime): Int = offsetDateTime.toLocalTime.getNano

  implicit final val lessThanForOffsetDateTime: LessThan[OffsetDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lt = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) < nanos(x))
    if (lt) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForOffsetDateTime: LessThanOrEqual[OffsetDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lte = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) <= nanos(x))
    if (lte) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForOffsetDateTime: GreaterThan[OffsetDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gt = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) > nanos(x))
    if (gt) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForOffsetDateTime: GreaterThanOrEqual[OffsetDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gte = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) >= nanos(x))
    if (gte) List.empty else List(s"_ >= $x")
  }

}

object OffsetDateTimeConstraints extends OffsetDateTimeConstraints
