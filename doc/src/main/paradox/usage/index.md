# Usage

## Dependency

@@dependency[sbt,Maven,Gradle] { group="$group$" artifact="$name.core$" version="$version$" }


## Numeric Types (Double, Float, Int, Long, Short)

By default, implementations of the following operators are available for numeric types:

* <
* <=
* \>
* \>=

Example:

@@snip[Example.scala]($root$/src/main/scala/usage/NumericExample.scala) { #numeric_example }


## String

By default, implementations of the following operators are available for strings:

* _.length <
* _.length <=
* _.length >
* _.length >=
* matches

Example:

@@snip[Example.scala]($root$/src/main/scala/usage/StringExample.scala) { #string_example }


## Selective Imports

In case you do not want to import all default constraint type class implementations, you can also do so selectively.  
Simply check the traits mixed into <i>DefaultConstraints</i> which all come with a respective companion object.
