// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.toolkit

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.ysb33r.grolifant.api.exec.AbstractCommandExecSpec
import org.ysb33r.grolifant.api.exec.ExternalExecutable

@CompileStatic
final class AboutToolkitExecSpec extends AbstractCommandExecSpec {
  /**
   * Construct class and attach it to specific project.
   *
   * @param project Project this exec spec is attached.
   * @param registry Registry used to resolve executables.
   */
  /*TODO protected*/ AboutToolkitExecSpec(Project project, ExternalExecutable registry) {
    super(project, registry)
    setExecutable 'about'
  }
}
