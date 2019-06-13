lazy val root = (project in file("."))
  .aggregate(core, doc)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  // root intentionally does not contain any code, so don't publish
  .settings(CommonSettingsPlugin.publishSettings(enabled = false))
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    // https: //www.scala-sbt.org/1.x/docs/Cross-Build.html#Cross+building+a+project
    crossScalaVersions := Nil,
    name := "factories-root"
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    libraryDependencies ++= Dependencies.coreLibraries,
    name := "factories"
  )

lazy val doc = (project in file("doc"))
  .dependsOn(core)
  .enablePlugins(GhpagesPlugin, GitBranchPrompt, GitVersioning, ParadoxSitePlugin, ParadoxPlugin, PreprocessPlugin)
  // this project is not supposed to be used externally, so don't publish
  .settings(CommonSettingsPlugin.publishSettings(enabled = false))
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
    // make sure that the example codes compiles in all cross Scala versions
    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    // only delete index.html which to put a new latest version link in to place but retain the old doc
    includeFilter in ghpagesCleanSite := "index.html",
    name := "factories-doc",
    // trigger dump-license-report in all other projects and rename the output
    // (paradox uses the first heading as link name in '@@@index' containers AND cannot handle variables in links)
    (mappings in Compile) in paradoxMarkdownToHtml ++= Seq(
      (core / dumpLicenseReport).value / ((core / licenseReportTitle).value + ".md") -> "licenses/core.md"
    ),
    // trigger code compilation of example code
    paradox in Compile := {
      val _ = (compile in Compile).value
      (paradox in Compile).value
    },
    // properties to be accessible from within the documentation
    paradoxProperties ++= Map(
      "group" -> organization.value,
      "name.core" -> (core / name).value,
      "version" -> version.value
    ),
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    // used to update the "latest" link in the doc index.html which is not managed by paradox
    preprocessVars in Preprocess := Map("version" -> version.value),
    // move the paradox source into a sub-directory named after the current version
    siteSubdirName in Paradox := version.value
  )
