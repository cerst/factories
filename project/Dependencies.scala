import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val ScalaCheck = "1.14.0"
    val Scalatest = "3.0.8"
    val Silencer = "1.4.1"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // BSD-3-Clause
    val ScalaCheck = "org.scalacheck" %% "scalacheck" % Version.ScalaCheck
    // Apache-2.0
    val Scalatest = "org.scalatest" %% "scalatest" % Version.Scalatest
    val SilencerCompilerPlugin = compilerPlugin("com.github.ghik" %% "silencer-plugin" % Version.Silencer)
    val SilencerLib = "com.github.ghik" %% "silencer-lib" % Version.Silencer
  }

  val coreLibraries: Seq[ModuleID] = Seq(
    Library.ScalaCheck % Test,
    Library.Scalatest % Test,
    Library.SilencerCompilerPlugin,
    Library.SilencerLib % Provided
  )
}
