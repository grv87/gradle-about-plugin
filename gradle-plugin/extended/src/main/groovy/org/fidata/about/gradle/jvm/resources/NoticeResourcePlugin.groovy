// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm.resources

import static org.fidata.jvm.utils.JarUtils.META_INF
import groovy.transform.CompileStatic
import java.nio.file.Path
import org.fidata.about.gradle.AbstractAboutPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.language.jvm.tasks.ProcessResources

@CompileStatic
class NoticeResourcePlugin implements Plugin<Project> {
  private Project project

  Path noticeFile

  @Override
  void apply(Project project) {
    this.@project = project

    noticeFile = ((AbstractAboutPlugin)project.plugins.getPlugin(AbstractAboutPlugin)).about.noticeFile.value

    project.tasks.withType(ProcessResources).configureEach { ProcessResources processResources ->
      processResources.from(noticeFile) { CopySpec copySpec -> // TOTEST: null ?
        copySpec.into META_INF
      }
    }
  }
}
