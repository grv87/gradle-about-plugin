package org.fidata.gradle.about

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutPlugin implements Plugin<Project> {
  public static final ABOUT_EXTENSION_NAME = 'about'

  @Override
  void apply(Project project) {
    project.extensions.create ABOUT_EXTENSION_NAME, AboutExtension, project
  }
}
