package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.internal.os.OperatingSystem
import org.ysb33r.grolifant.api.exec.AbstractToolExtension
import org.ysb33r.grolifant.api.exec.ResolvableExecutable

@CompileStatic
class AboutToolkitExtension extends AbstractToolExtension {
  /**
   * Name of aboutToolkit extension
   */
  static final String NAME = 'aboutToolkit'


  protected AboutToolkitExtension(Project project) {
    super(project)
    executable path: OperatingSystem.current().findInPath('about')
  }

  protected AboutToolkitExtension(Task task, String projectExtName) {
    super(task, projectExtName)
  }

  @Override
  @Input
  ResolvableExecutable getResolvableExecutable() {
    super.resolvableExecutable
  }
}
