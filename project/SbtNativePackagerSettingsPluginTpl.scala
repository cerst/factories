import com.typesafe.sbt.packager.Keys.bashScriptExtraDefines
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt._

object SbtNativePackagerSettingsPluginTpl extends AutoPlugin {

  override def requires = JavaAppPackaging

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {

    // Configure the sbt-native-packager bash (start) script to include system properties overriding the location of
    // * application.conf
    // * log4j2.xml
    // --
    // Note that during packaging, 'src/universal/**' is copied to '${app_home}/../**'
    // e.g.: 'src/universal/conf' -> '${app_home}/../conf'
    // i.e. you can package production specific config (and more) that way
    bashScriptExtraDefines ++= Seq(
      """addJava "-Dconfig.file=${app_home}/../conf/application.conf"""",
      """addJava "-Dlog4j.configurationFile=${app_home}/../conf/log4j2.xml""""
    )
  }

}
