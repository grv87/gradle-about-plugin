// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm.resources

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AbstractAboutPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.language.jvm.tasks.ProcessResources

@CompileStatic
class NoticePlugin implements Plugin<Project> {
  private Project project

  Provider<RegularFile> noticeFile

  @Override
  void apply(Project project) {
    this.@project = project

    noticeFile = project.layout.file(project.providers.provider {
      project.file(project.plugins.getPlugin(AbstractAboutPlugin).about.noticeFile.value)
    })

    project.tasks.withType(ProcessResources).configureEach { ProcessResources processResources ->
      processResources.from(noticeFile) { CopySpec copySpec -> // TOTEST: null ?
        copySpec.into 'META-INF'
      }
    }
  }
}
