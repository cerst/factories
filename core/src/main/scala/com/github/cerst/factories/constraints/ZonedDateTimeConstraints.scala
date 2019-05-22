package com.github.cerst.factories.constraints

import java.time.ZonedDateTime

import com.github.cerst.factories.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

/**
  * Implementation Note:
  * <p>
  * All constraints implement an instant-based comparison based on [[java.time.ZonedDateTime#isAfter isAfter]],
  * [[java.time.ZonedDateTime#isBefore isBefore]] or [[java.time.ZonedDateTime#isEqual isEqual]]. This is because:
  * <ul>
  * <li>[[java.time.ZonedDateTime#compareTo compareTo]] has different semantics</i>
  * <li>there is no <i>isBeforeOrSame</i> or <i>isAfterOrSame</i> for [[java.time.ZonedDateTime ZonedDateTime]]</li>
  * <li>(re-) implementation is more efficient that calling e.g. [[java.time.ZonedDateTime#isBefore isBefore]] and [[java.time.ZonedDateTime#isEqual isEqual]] in sequence</li>
  * <li>using the same implementation for all constraints instead of e.g. using [[java.time.ZonedDateTime#isBefore isBefore]] for implementing [[com.github.cerst.factories.syntax.LessThan]] increases readability</li>
  * <ul>
  */
trait ZonedDateTimeConstraints {

  private final def nanos(zonedDateTime: ZonedDateTime): Int = zonedDateTime.toLocalTime.getNano

  implicit final val lessThanForZonedDateTime: LessThan[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lt = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) < nanos(x))
    if (lt) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForZonedDateTime: LessThanOrEqual[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lte = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) <= nanos(x))
    if (lte) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForZonedDateTime: GreaterThan[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gt = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) > nanos(x))
    if (gt) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForZonedDateTime: GreaterThanOrEqual[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gte = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) >= nanos(x))
    if (gte) List.empty else List(s"_ >= $x")
  }

}

object ZonedDateTimeConstraints extends ZonedDateTimeConstraints
