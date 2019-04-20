import com.typesafe.sbt.GitPlugin.autoImport.git
import sbt.Keys.{name, sbtVersion, scalaVersion, version}
import sbt.{AutoPlugin, Def, Plugins}
import sbtbuildinfo.BuildInfoPlugin
import sbtbuildinfo.BuildInfoPlugin.autoImport._

/**
  * Automatic settings for the BuildInfoPlugin.<br/>
  * <i>buildInfoPackage</i> is not modified (default: buildInfo)
  */
object BuildInfoSettingsPluginTpl extends AutoPlugin {

  override def requires: Plugins = BuildInfoPlugin

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {
    // values to be generated of members of the build info object
    buildInfoKeys := Seq[BuildInfoKey](
      BuildInfoKey.setting(git.gitHeadCommit),
      name,
      sbtVersion,
      scalaVersion,
      version
    )
    // add the build time as build info key AND
    // generate a method which produces a JSON object string containing all build info key-values
    buildInfoOptions ++= Seq(BuildInfoOption.BuildTime, BuildInfoOption.ToJson)
  }

}
