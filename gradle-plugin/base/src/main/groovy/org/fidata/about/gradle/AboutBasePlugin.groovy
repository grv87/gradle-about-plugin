package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.AboutExtended
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutBasePlugin implements Plugin<Project> {
  public static final String ABOUT_EXTENSION_NAME = 'about'

  @Override
  void apply(Project project) {
    final AboutExtended about = AboutExtended.readFromFile(project.file("${ project.name }.ABOUT"))

    project.extensions.add ABOUT_EXTENSION_NAME, about
  }
}
