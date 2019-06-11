// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.model.About
import org.fidata.about.model.License
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.ivy.IvyModuleDescriptorDescription
import org.gradle.api.publish.ivy.IvyModuleDescriptorLicense
import org.gradle.api.publish.ivy.IvyModuleDescriptorSpec
import org.gradle.api.publish.ivy.IvyPublication
import org.gradle.api.publish.ivy.plugins.IvyPublishPlugin

@CompileStatic
class AboutIvyPublishPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.plugins.withType(AbstractAboutPlugin) { ->
      About about = project.extensions.getByType(About)

      project.plugins.withType(IvyPublishPlugin) {
        project.extensions.getByType(PublishingExtension).publications.withType(IvyPublication) { IvyPublication ivyPublication ->
          IvyModuleDescriptorSpec descriptor = ivyPublication.descriptor

          descriptor.description { IvyModuleDescriptorDescription descriptorDescription ->
            descriptorDescription.text.set about.description.value
            descriptorDescription.homepage.set about.homepageUrl.value.toString()
          }

          about.licenses.each { License license ->
            descriptor.license { IvyModuleDescriptorLicense descriptorLicense ->
              descriptorLicense.name.set license.name.value.toString()
              descriptorLicense.url.set license.url.value.toString()
            }
          }
        }
      }
    }
  }
}
