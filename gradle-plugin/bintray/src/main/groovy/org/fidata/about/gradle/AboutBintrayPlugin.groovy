// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import com.jfrog.bintray.gradle.BintrayPlugin
import org.fidata.about.maven.MavenAbout
import org.gradle.api.Plugin
import org.gradle.api.Project

class AboutBintrayPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.pluginManager.apply MavenAboutPlugin

    MavenAbout mavenAbout = project.extensions.getByType(MavenAbout)

    project.plugins.withType(BintrayPlugin) {
      project.extensions.configure(BintrayExtension) { BintrayExtension extension ->
        extension.pkg.with {
          version.name = ''
          version.vcsTag = '' // TODO
          desc = project.version.toString() == '1.0.0' ? project.description : rootProjectConvention.changeLogTxt.get().toString()
          labels = projectConvention.tags.get().toArray(new String[0])
          licenses = [projectConvention.license].toArray(new String[1])
          vcsUrl = rootProjectConvention.vcsUrl.get()
          // pkg.version.attributes // Attributes to be attached to the version
        }
      }
    }
  }
}
