// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.toolkit.tasks

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.fidata.about.gradle.toolkit.AboutToolkitExecSpec
import org.fidata.about.gradle.toolkit.AboutToolkitExtension
import org.gradle.api.file.FileTree
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Console
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.util.PatternFilterable
import org.ysb33r.grolifant.api.exec.AbstractExecWrapperTask

@CompileStatic
@PackageScope
abstract class AbstractAboutToolkitTask extends AbstractExecWrapperTask<AboutToolkitExecSpec, AboutToolkitExtension> {
  /**
   * Path to a file, directory or .zip archive containing .ABOUT files.
   *
   * Note that some aboutcode-toolkit commands may not support .zip archives.
   * Check against the version you have to be sure
   */
  @Internal
  final Property<File> location = project.objects.property(File)

  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: src/attributecode/util.py#is_about_file
   * SnippetCopyrightText: Copyright (c) 2013-2018 nexB Inc. http://www.nexb.com/ - All rights reserved.
   * LicenseInfoInSnippet: Apache-2.0
   */
  private boolean isAboutFile(String path) {
    path = path.toLowerCase(Locale.ROOT)
    path.endsWith('.about') && path != '.about'
  }
  // @third party code - END aboutcode-toolkit

  @InputFiles
  protected final FileTree aboutFiles = project.fileTree { ->
    File locationFile = location.get()
    if (!locationFile.isDirectory() && locationFile.name.endsWith('.zip')) {
      project.zipTree(locationFile)
    } else {
      project.fileTree(locationFile)
    }
  }
  /*
   * CAVEAT:
   * aboutcode-toolkit checks file extension even when single filename is passed
   */
  .matching { PatternFilterable patternFilterable ->
    patternFilterable.include { String path ->
      isAboutFile(path)
    }
  }

  /**
   * Show all error and warning messages.
   */
  @Console
  final Property<Boolean> verbose = project.objects.property(Boolean).convention project.provider { (project.logging.level ?: project.gradle.startParameter.logLevel) <= LogLevel.DEBUG }

  @Lazy // To avoid leak of this reference
  // TOTEST
  private AboutToolkitExtension aboutToolkitExtension = extensions.create(AboutToolkitExtension.NAME, AboutToolkitExtension, this)

  @Override
  protected final AboutToolkitExtension getToolExtension() {
    aboutToolkitExtension
  }

  @Override
  protected final AboutToolkitExecSpec createExecSpec() {
    new AboutToolkitExecSpec(project, toolExtension.resolver)
  }

  protected List<Object> getOptions() {
    null
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec.workingDir project.projectDir
    List<Object> options = this.options
    if (options) {
      execSpec.cmdArgs options
    }
    if (verbose.get()) {
      execSpec.cmdArgs '--verbose'
    }
    execSpec.cmdArgs location
    execSpec
  }
}
