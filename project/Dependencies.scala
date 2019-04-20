import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val ScalaCheck = "1.14.0"
    val Scalatest = "3.0.5"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // BSD-3-Clause
    val ScalaCheck = "org.scalacheck" % "scalacheck_2.12" % Version.ScalaCheck
    // Apache-2.0
    val Scalatest = "org.scalatest" %% "scalatest" % Version.Scalatest

  }

  val coreLibraries: Seq[ModuleID] = Seq(Library.ScalaCheck % Test, Library.Scalatest % Test)
}
