package org.fidata.about.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.language.jvm.tasks.ProcessResources

class LicenseCopyingPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    

    project.tasks.withType(ProcessResources).configureEach { ProcessResources processResources ->
      processResources.from(LICENSE_FILE_NAMES) { CopySpec copySpec ->
        copySpec.into 'META-INF'
      }
    }

  }
}
