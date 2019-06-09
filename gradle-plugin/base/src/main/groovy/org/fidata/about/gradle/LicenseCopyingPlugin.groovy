// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import groovy.transform.CompileStatic
import java.nio.file.Path
import org.fidata.about.model.About
import org.fidata.about.model.FileTextField
import org.fidata.about.model.License
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.language.jvm.tasks.ProcessResources

@CompileStatic
class LicenseCopyingPlugin implements Plugin<Project> {
  private static void processLicenseResource(ProcessResources processResources, FileTextField fileTextField) {
    Path file = fileTextField.value
    if (file != null) {
      processResources.from(file) { CopySpec copySpec ->
        copySpec.into 'META-INF'
      }
    }
  }

  @Override
  void apply(Project project) {
    project.extensions.configure(About) { About about ->
      project.tasks.withType(ProcessResources).configureEach { ProcessResources processResources ->
        about.licenses.each { License license ->
          license.files.each { FileTextField licenseFile ->
            processLicenseResource processResources, licenseFile
          }
        }
        processLicenseResource processResources, about.noticeFile
      }
    }
  }
}
