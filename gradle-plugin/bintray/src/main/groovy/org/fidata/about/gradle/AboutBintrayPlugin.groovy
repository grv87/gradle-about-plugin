// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import groovy.transform.CompileStatic
import org.fidata.about.maven.MavenAbout
import org.fidata.bintray.SpdxToBintrayLicenseConverter
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
          // TODO desc = project.version.toString() == '1.0.0' ? project.description : rootProjectConvention.changeLogTxt.get().toString()
          // TODO labels = projectConvention.tags.get().toArray(new String[0])
          licenses = SpdxToBintrayLicenseConverter.convert(mavenAbout.licenseExpression.value).toArray(new String[0])
          // TODO vcsUrl = rootProjectConvention.vcsUrl.get()
          // pkg.version.attributes // Attributes to be attached to the version
        }
      }
    }
  }
}
