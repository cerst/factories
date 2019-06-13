import com.typesafe.sbt.GitPlugin.autoImport.git
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.{HeaderLicense, headerLicense}
import sbt.Keys._
import sbt._

object CommonSettingsPlugin extends CommonSettingsPluginTpl {

  lazy val crossScalaVersions = List("2.12.8", "2.13.0")
  lazy val scalaVersionValue = "2.13.0"

  // the rationale for placing settings defs here is that they should (or can) not be updated automatically using the scala-base-sync script
  // in the following, organizationName and startYear would also be required by sbt-header to generate ready-made license headers
  override lazy val projectSettings: Seq[Def.Setting[_]] = {
    tplProjectSettingsPlus(scalaVersionValue)(
      developers := List(Developer("cerst", "Constantin Gerstberger", "", url("https://github.com/cerst"))),
      git.baseVersion := "0.4.0",
      // basically only needed for sbt-ghpages
      git.remoteRepo := "git@github.com:cerst/factories.git",
      headerLicense := Some(HeaderLicense.MIT(startYear.value.get.toString, organizationName.value)),
      homepage := Some(url("https://github.com/cerst/factories")),
      licenses += "MIT" -> url("https://opensource.org/licenses/MIT"),
      organization := "com.github.cerst",
      organizationName := "Constantin Gerstberger",
      publishMavenStyle := true,
      resolvers ++= Dependencies.resolvers,
      scmInfo := Some(ScmInfo(homepage.value.get, git.remoteRepo.value)),
      startYear := Some(2019)
    )
  }

  def publishSettings(enabled: Boolean): Seq[Def.Setting[_]] = {
    if (!enabled) {
      skip in publish := true
    } else {
      publishTo := Some {
        if (isSnapshot.value) {
          Opts.resolver.sonatypeSnapshots
        } else {
          Opts.resolver.sonatypeStaging
        }
      }
    }
  }

}
