// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.model.About
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
abstract class AbstractAboutPlugin<T extends About> implements Plugin<Project> {
  public static final String ABOUT_EXTENSION_NAME = 'about'

  private T about

  T getAbout() {
    this.@about
  }

  private File aboutFile

  File getAboutFile() {
    this.@aboutFile
  }

  protected abstract T readFromFile()

  @Override
  final void apply(Project project) {
    /*
     * We don't use lazy configuration since:
     * 1. Values from ABOUT are most probably used in each build.
     *    Lazy configuration would just slow down everything
     * 2. Read values are immutable anyway
     */
    this.@aboutFile = project.file("${ project.name }.ABOUT")
    this.@about = readFromFile()

    project.extensions.add ABOUT_EXTENSION_NAME, about
  }
}
