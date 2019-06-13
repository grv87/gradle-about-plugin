// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.toolkit

import groovy.transform.CompileStatic
import org.fidata.about.gradle.toolkit.tasks.AboutAttrib
import org.fidata.about.gradle.toolkit.tasks.AboutCheck
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
final class AboutToolkitBasePlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    [
      AboutAttrib,
      AboutCheck
    ].each { Class taskClass ->
      project.extensions.extraProperties.set(taskClass.simpleName, taskClass)
    }
  }
}
