package org.fidata.about.gradle.tasks

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AboutToolkitExecSpec
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Console
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile

@CompileStatic
class AboutAttrib extends AbstractAboutToolkitTask {
  @OutputFile
  final RegularFileProperty output = project.objects.fileProperty()

  @InputFile
  @Optional
  final RegularFileProperty template = project.objects.fileProperty()

  @Input
  @Optional
  final MapProperty<String, String> variables = project.objects.mapProperty(String, String)

  @Console
  final Property<Boolean> quiet = project.objects.property(Boolean).convention(Boolean.FALSE)

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec.command 'attrib'
    execSpec
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec = super.configureExecSpec(execSpec)
    execSpec.cmdArgs output
    execSpec
  }


  @Override
  protected List<Object> getOptions() {
    List<Object> options = []
    if (template.getOrNull()) {
      options.add '--template'
      options.add template
    }
    variables.get().each { String key, String value ->
      options.add '--vartext'
      options.add "$key=$value"
    }
    if (quiet.get()) {
      options.add '--quiet'
    }
    options
  }
}
