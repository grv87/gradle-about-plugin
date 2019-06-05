package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.gradle.tasks.AboutAttrib
import org.fidata.about.gradle.tasks.AboutCheck
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutToolkitBasePlugin implements Plugin<Project> {
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
