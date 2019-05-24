package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.Task
import org.ysb33r.grolifant.api.exec.AbstractToolExtension

@CompileStatic
class AboutToolkitExtension extends AbstractToolExtension {
  protected AboutToolkitExtension(Project project) {
    super(project)
  }

  protected AboutToolkitExtension(Task task, String projectExtName) {
    super(task, projectExtName)
  }
}
