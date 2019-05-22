# Changelog

## 0.3.0+
* Add constraint implementations for 
  * _java.time.Duration_
  * _java.time.Instant_
  * _java.time.OffsetDateTime._
  * _java.time.ZonedDateTime._
  * _scala.BigDecimal_
  * _scala.BigInt_
  * _scala.concurrent.Duration._
* Only list dependencies with scope _Compile_ or _Provided_ in the documentation

## 0.2.0
**Breaking Changes**:
* rename _safeCreate_ to _createEither_ and _unsafeCreate_ to _create_ to be consistent with newly added _createTry_

Other Changes:
* Add _createTry_ as a third return type option for factory methods
* Extend documentation
* Error messages print the type to be created _B_ within single quotes (just as is the case with the provided value _a_)
* Remove license listing for the _doc_ project as it isn't published

## 0.1.0
* First release
