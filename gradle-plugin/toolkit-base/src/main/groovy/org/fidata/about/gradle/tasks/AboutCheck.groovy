// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.tasks

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AboutToolkitExecSpec

@CompileStatic
class AboutCheck extends AbstractAboutToolkitTask {
  {
    // Always run in verbose mode
    verbose.convention Boolean.TRUE
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    execSpec.command 'check'
    execSpec
  }
}
