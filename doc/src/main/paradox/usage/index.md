# Usage

## Dependency

@@dependency[sbt,Maven,Gradle] { group="$group$" artifact="$name.core$" version="$version$" }

## Numeric Types (Double, Float, Int, Long, Short)

@@snip[Example.scala]($root$/src/main/scala/usage/NumericExample.scala) { #numeric_example }

Full list of constraints:

* <
* <=
* \>
* \>=

## String

@@snip[Example.scala]($root$/src/main/scala/usage/StringExample.scala) { #string_example }

Full list of constraints:

* _.length <
* _.length <=
* _.length >
* _.length >=
* matches
