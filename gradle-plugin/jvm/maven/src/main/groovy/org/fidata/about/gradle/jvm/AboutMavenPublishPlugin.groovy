// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm

import groovy.transform.CompileStatic
import org.fidata.about.model.jvm.maven.LicenseExtended
import org.fidata.about.model.jvm.maven.MavenAbout
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomLicense
import org.gradle.api.publish.maven.MavenPomLicenseSpec
import org.gradle.api.publish.maven.MavenPomScm
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin

@CompileStatic
class AboutMavenPublishPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.plugins.withType(MavenAboutPlugin) { ->
      MavenAbout mavenAbout = project.extensions.getByType(MavenAbout)

      project.plugins.withType(MavenPublishPlugin) {
        project.extensions.getByType(PublishingExtension).publications.withType(MavenPublication) { MavenPublication mavenPublication ->
          MavenPom pom = mavenPublication.pom
          pom.name.set mavenAbout.name.value
          pom.description.set mavenAbout.description.value
          pom.url.set mavenAbout.homepageUrl.value.toString()
          /* TODO
          properties.set [
            myProp: "value",
            "prop.with.dots": "anotherValue"
          ]*/
          pom.licenses { MavenPomLicenseSpec licenseSpec ->
            mavenAbout.licenses.each { LicenseExtended aboutLicense ->
              licenseSpec.license { MavenPomLicense license ->
                license.name.set aboutLicense.name.value
                license.url.set aboutLicense.url.value.toString()
                license.comments.set aboutLicense.comments.value
                license.distribution.set aboutLicense.distribution.value
              }
            }
          }
          /* TODO
          developers {
            developer {
              id = 'johnd'
              name = 'John Doe'
              email = 'john.doe@example.com'
            }
          }*/
          pom.scm { MavenPomScm scm ->
            scm.connection.set mavenAbout.vcsConnectionUrl.value.toString()
            scm.developerConnection.set mavenAbout.getVcsDeveloperConnectionUrl().value.toString()
            scm.url.set mavenAbout.vcsUrl.value.toString()
          }
        }
      }
    }

    [
      'nebula.maven-developer',
      'nebula.maven-apache-license',
    ].each { String pluginId ->
      project.plugins.withId(pluginId) {
        throw new IllegalStateException("Plugin org.fidata.about.maven-publish replaces $pluginId plugin. They should not be used together")
      }
    }
  }
}
