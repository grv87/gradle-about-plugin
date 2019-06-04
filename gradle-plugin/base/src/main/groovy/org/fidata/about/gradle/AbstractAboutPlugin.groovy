package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.model.About
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
abstract class AbstractAboutPlugin<T extends About> implements Plugin<Project> {
  public static final String ABOUT_EXTENSION_NAME = 'about'

  protected abstract T readFromFile(File src)

  @Override
  final void apply(Project project) {
    final T about = readFromFile(project.file("${ project.name }.ABOUT"))

    project.extensions.add ABOUT_EXTENSION_NAME, about
  }

  protected void doApply(Project project) {
  }
}
