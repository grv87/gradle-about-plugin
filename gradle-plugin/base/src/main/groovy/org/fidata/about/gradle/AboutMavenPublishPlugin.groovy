package org.fidata.about.gradle

import groovy.transform.CompileStatic
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
    project.plugins.apply AboutBasePlugin
    AboutExtended aboutExtended = project.extensions.getByType(AboutExtended)
    project.plugins.withType(MavenPublishPlugin) {
      project.extensions.getByType(PublishingExtension).publications.withType(MavenPublication) { MavenPublication mavenPublication ->
        MavenPom pom = mavenPublication.pom
        pom.name.set aboutExtended.name.value
        pom.description.set aboutExtended.description.value
        pom.url.set aboutExtended.homepageUrl.value.toString()
        /* TODO
        properties.set [
          myProp: "value",
          "prop.with.dots": "anotherValue"
        ]*/
        pom.licenses { MavenPomLicenseSpec licenseSpec ->
          aboutExtended.licenses.each { LicenseExtended aboutLicense ->
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
          scm.connection.set aboutExtended.vcsConnectionUrl.value.toString()
          scm.developerConnection.set aboutExtended.getVcsDeveloperConnectionUrl().value.toString()
          scm.url.set aboutExtended.vcsUrl.value.toString()
        }
      }
    }
  }
}
