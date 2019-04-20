def publishSettings(enabled: Boolean): Seq[Def.Setting[_]] = {
  if(!enabled){
    Seq(skip in publish := true)
  } else {
    // refined as needed for publishing
    // publishTo := ???
    Seq()
  }
}

lazy val root = (project in file("."))
  .aggregate(core, doc)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  // this project is not supposed to be used externally, so don't publish
  .settings(publishSettings(enabled = false))
  .settings(
    name := "factories-root"
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(publishSettings(enabled = true))
  .settings(
    libraryDependencies ++= Dependencies.coreLibraries,
    name := "factories"
  )

// TODO: set-up a gh-pages branch as explained here: https://github.com/sbt/sbt-ghpages#initializing-the-gh-pages-branch
lazy val doc = (project in file("doc"))
  .dependsOn(core)
  .enablePlugins(GhpagesPlugin, GitBranchPrompt, GitVersioning, ParadoxSitePlugin, ParadoxPlugin, PreprocessPlugin)
  // this project is not supposed to be used externally, so don't publish
  .settings(publishSettings(enabled = false))
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
    // only delete index.html which to put a new latest version link in to place but retain the old doc
    includeFilter in ghpagesCleanSite := "index.html",
    name := "factories-doc",
    // trigger dump-license-report in all other projects and rename the output
    // (paradox uses the first heading as link name in '@@@index' containers AND cannot handle variables in links)
    (mappings in Paradox) in paradoxMarkdownToHtml ++= Seq(
      (core / dumpLicenseReport).value / ((core / licenseReportTitle).value + ".md") -> "licenses/core.md",
      dumpLicenseReport.value / (licenseReportTitle.value + ".md") -> "licenses/doc.md"
    ),
    // trigger code compilation of example code
    paradox in Compile := {
      val _ = (compile in Compile).value
      (paradox in Paradox).value
    },
    // properties to be accessible from within the documentation
    paradoxProperties in Paradox ++= Map(
      "group" -> organization.value,
      "name.core" -> (core / name).value,
      "version" -> version.value
    ),
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    // used to update the "latest" link in the doc index.html which is not managed by paradox
    preprocessVars in Preprocess := Map("version" -> version.value),
    // sbt-site by default assumes Paradox sources under "src/paradox" (which is wrong)
    sourceDirectory in Paradox := sourceDirectory.value / "main" / "paradox",
    // move the paradox source into a sub-directory named after the current version
    siteSubdirName in Paradox := version.value
  )
