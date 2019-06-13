// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.toolkit.tasks

import groovy.transform.CompileStatic
import org.fidata.about.gradle.toolkit.AboutToolkitExecSpec
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Console
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile

/**
 * Generates an attribution document using .ABOUT files.
 */
@CompileStatic
final class AboutAttrib extends AbstractAboutToolkitTask {
  /**
   * Path where to write the attribution document.
   */
  @OutputFile
  final RegularFileProperty output = project.objects.fileProperty()

  /**
   * Optional custom attribution template to generate the attribution document.
   *
   * If not provided the default built-in template is used.
   */
  @InputFile
  @Optional
  final RegularFileProperty template = project.objects.fileProperty()

  /**
   * Variables for use in a custom attribution template.
   */
  @Input
  @Optional
  final MapProperty<String, String> variables = project.objects.mapProperty(String, String)

  /**
   * Do not print error or warning messages.
   */
  @Console
  final Property<Boolean> quiet = project.objects.property(Boolean).convention project.provider { (project.logging.level ?: project.gradle.startParameter.logLevel) >= LogLevel.QUIET }

  @Override
  protected List<Object> getOptions() {
    List<Object> options = []
    if (template.present) {
      options.addAll '--template', template
    }
    options.addAll(((Map<String, String>)variables.get()).collectMany { String key, String value ->
      ['--vartext', "$key=$value"]
    })
    if (quiet.get()) {
      options.add '--quiet'
    }
    options
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec = super.configureExecSpec(execSpec)
    execSpec.command 'attrib'
    execSpec.cmdArgs output
    execSpec
  }
}
