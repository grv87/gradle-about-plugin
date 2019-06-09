// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.tasks

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AboutToolkitExecSpec
import org.fidata.about.gradle.AboutToolkitExtension
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Console
import org.gradle.api.tasks.InputFile
import org.ysb33r.grolifant.api.exec.AbstractExecWrapperTask

@CompileStatic
abstract class AbstractAboutToolkitTask extends AbstractExecWrapperTask<AboutToolkitExecSpec, AboutToolkitExtension> {
  @InputFile // TODO
  final RegularFileProperty location = project.objects.fileProperty()

  @Console
  final Property<Boolean> verbose = project.objects.property(Boolean).convention project.provider { (project.logging.level ?: project.gradle.startParameter.logLevel) <= LogLevel.DEBUG }

  // TODO @Lazy
  private final AboutToolkitExtension aboutToolkitExtension = extensions.create(AboutToolkitExtension.NAME, AboutToolkitExtension, this)

  @Override
  protected AboutToolkitExtension getToolExtension() {
    aboutToolkitExtension
  }

  @Override
  protected AboutToolkitExecSpec createExecSpec() {
    new AboutToolkitExecSpec(project, toolExtension.resolver)
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec.workingDir project.projectDir
    List<Object> options = this.options
    if (options) {
      execSpec.cmdArgs options
    }
    execSpec.cmdArgs location
    execSpec
  }

  protected List<Object> getOptions() {
    null
  }
}
