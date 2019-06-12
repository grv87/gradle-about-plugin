// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm.resources

import static org.fidata.jvm.utils.JarUtils.META_INF
import groovy.transform.CompileStatic
import java.nio.file.Path
import org.fidata.about.gradle.AbstractAboutPlugin
import org.fidata.about.model.License
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.language.jvm.tasks.ProcessResources

@CompileStatic
class LicenseResourcesPlugin implements Plugin<Project> {
  private Project project

  List<Path> licenseFiles

  @Override
  void apply(Project project) {
    this.@project = project

    licenseFiles = ((AbstractAboutPlugin)project.plugins.getPlugin(AbstractAboutPlugin)).about.licenses.collectMany { License license ->
      license.files*.value
    }

    project.tasks.withType(ProcessResources).configureEach { ProcessResources processResources ->
      processResources.from(licenseFiles) { CopySpec copySpec ->
        copySpec.into META_INF
      }
    }
  }
}
