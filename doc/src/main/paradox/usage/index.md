# Usage


## Dependency

$name.core$ is available for Scala _2.12_ and _2.13_.

@@dependency[sbt,Maven,Gradle] { group="$group$" artifact="$name.core$_2.13" version="$version$" }


## Numeric Example

@@snip[Example.scala]($root$/src/main/scala/usage/NumericExample.scala) { #numeric_example }


## String Example

@@snip[Example.scala]($root$/src/main/scala/usage/StringExample.scala) { #string_example }


## Constraint Syntax and Instances

Factories comes with instances of _<_, _<=_, _>_ and _>=_ for:

* _BigDecimal_
* _BigInt_
* _Double_
* _Duration (java.time & scala.concurrent)_
* _Float_
* _Instant (java.time)_
* _Int_
* _Long_
* _OffsetDateTime (java.time)_
* _Byte_
* _Short_
* _ZonedDateTime (java.time)_

Furthermore, there are instances of _\_.length <_, _\_.length <=_, _\_.length >_, _\_.length >=_ and _matches_ for:

* _String_ 


## Selective Imports

In case you do not want to import all default constraint type class implementations, you can also do so selectively.  
Simply check the traits mixed into <i>DefaultConstraints</i> which all come with a respective companion object.
