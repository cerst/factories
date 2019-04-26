# factories

@@@ index

* [Changelog](changelog/index.md)
* [Licenses](licenses/index.md)
* [Usage](usage/index.md)

@@@

Standardized Scala factory methods with accumulated error messages.

## Overview

The primary use case of _factories_ is implementing your typical factory/ _apply_ method of a value class. However,
the library does not make  assumptions about what a factory method creates, so it should be applicable to other
contexts as well. 

The creation of factories methods is supported by way of 

* a standardized (constraint) syntax and 
* typeclass-based implementations of said constraints for various types.

In this regard, typical examples for constraints are numeric range restrictions or strings matching a regular expression.

For more details, check the @ref:[Usage](usage/index.md) section.
