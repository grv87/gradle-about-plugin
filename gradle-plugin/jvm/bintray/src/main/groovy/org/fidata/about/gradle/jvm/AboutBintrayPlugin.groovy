// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import groovy.transform.CompileStatic
import org.fidata.about.model.jvm.maven.MavenAbout
import org.fidata.bintray.SpdxToBintrayLicensesConverter
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutBintrayPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.pluginManager.apply MavenAboutPlugin

    MavenAbout mavenAbout = project.extensions.getByType(MavenAbout)

    project.plugins.withType(BintrayPlugin) {
      project.extensions.configure(BintrayExtension) { BintrayExtension extension ->
        extension.pkg.with {
          // TODO version.name = ''
          version.vcsTag = '' // TODO
          desc = mavenAbout.description.value
          websiteUrl = mavenAbout.homepageUrl.value.toString()
          issueTrackerUrl = mavenAbout.issueManagement.url.value.toString()
          // TODO vcsUrl = rootProjectConvention.vcsUrl.get()
          /* TODO
          githubRepo
          githubReleaseNotesFile
          */
          licenses = SpdxToBintrayLicensesConverter.convert(mavenAbout.licenseExpression.value).toArray(new String[0])
          labels = mavenAbout.keywords*.value.toArray(new String[0])
          // pkg.version.attributes // Attributes to be attached to the version
        }
      }
    }
  }
}
