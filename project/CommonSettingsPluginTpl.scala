import com.typesafe.sbt.SbtLicenseReport.autoImport._
import sbt.Keys._
import sbt._

import scala.collection.immutable.Seq

trait CommonSettingsPluginTpl extends AutoPlugin {

  lazy val versionToFile = taskKey[Unit]("Print the version into /target/version-to-file/version")

  override def trigger: PluginTrigger = allRequirements

  protected def tplProjectSettingsPlus(scalaVersionValue: String)(additional: Def.Setting[_]*) = {
    licenseReportSettings ++
      scalaSettings(scalaVersionValue) ++
      scalacSettings(scalaVersionValue) ++
      additional
  }

  private def licenseReportSettings: Seq[Def.Setting[_]] = Seq(
    // The ivy configurations we'd like to grab licenses for.
    licenseConfigurations := Set(Compile, Provided).map(_.name),
    licenseReportStyleRules := Some("table, th, td {border: 1px solid black;}"),
    licenseReportTitle := normalizedName.value,
    licenseReportTypes := Seq(MarkDown)
  )

  private def scalaSettings(scalaVersionValue: String): Seq[Def.Setting[_]] = Seq(
    scalaVersion := scalaVersionValue,
    // used to read the version during release ("sbt version" causes much noise which makes extraction error-prone)
    versionToFile := {
      val file = target.value / "version-to-file" / "version"
      IO.write(file, version.value)
    }
  )

  // these settings are based on:
  //    http://tpolecat.github.io/2017/04/25/scalac-flags.html
  //    https://nathankleyn.com/2019/05/13/recommended-scalac-flags-for-2-13/
  private def scalacSettings(scalaVersionValue: String): Seq[Def.Setting[_]] = Seq(
    scalacOptions ++= Seq(
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-encoding",
      "utf-8", // Specify character encoding used by source files.
      "-explaintypes", // Explain type errors in more detail.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
      "-language:experimental.macros", // Allow macro definition (besides implementation and application)
      "-language:higherKinds", // Allow higher-kinded types
      "-language:implicitConversions", // Allow definition of implicit functions called views
      "-unchecked", // Enable additional warnings where generated code depends on assumptions.
      "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
      "-Xfatal-warnings", // Fail the compilation if there are any warnings.
      "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
      "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
      "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
      "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
      "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
      "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
      "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
      "-Xlint:option-implicit", // Option.apply used implicit view.
      "-Xlint:package-object-classes", // Class or object defined in package object.
      "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
      "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
      "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
      "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
      "-Ywarn-numeric-widen", // Warn when numerics are widened.
      "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
      "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
      "-Ywarn-unused:locals", // Warn if a local definition is unused.
      "-Ywarn-unused:params", // Warn if a value parameter is unused.
      "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
      "-Ywarn-unused:privates", // Warn if a private member is unused.
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
      "-Ybackend-parallelism",
      "8", // Enable paralellisation — change to desired number!
      "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
      "-Ycache-macro-class-loader:last-modified" // and macro definitions. This can lead to performance improvements.
    ),
    CrossVersion.partialVersion(scalaVersionValue) match {
      // these were removed in Scala 2.13 (not all flags seem to have been mentioned explicitly ...)
      //    https://github.com/scala/scala/pull/6505
      //    https://github.com/scala/scala/pull/6309#issuecomment-379250839
      case Some((2, scalaMajor)) if scalaMajor == 12 =>
        scalacOptions ++= Seq(
          "-Xfuture", // Turn on future language features.
          "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
          "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
          "-Xlint:unsound-match", // Pattern match may not be typesafe.
          "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
          "-Ypartial-unification" // Enable partial unification in type constructor inference.
        )
      case _ =>
        scalacOptions ++= Seq.empty

    },
    // "Note that the REPL can’t really cope with -Ywarn-unused:imports or -Xfatal-warnings so you should turn them off for the console."
    scalacOptions in (Compile, console) ~= (_.filterNot(
      Set(
        "-Ywarn-unused:imports",
        "-Xfatal-warnings"
      )))
  )

}
