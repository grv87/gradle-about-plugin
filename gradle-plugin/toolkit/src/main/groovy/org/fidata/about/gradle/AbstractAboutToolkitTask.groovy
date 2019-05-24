package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.ysb33r.grolifant.api.exec.AbstractExecWrapperTask

@CompileStatic
abstract class AbstractAboutToolkitTask extends AbstractExecWrapperTask<AboutToolkitExecSpec, AboutToolkitExtension> {
  @Override
  protected AboutToolkitExecSpec createExecSpec() {
    return null
  }

  @Override
  protected AboutToolkitExecSpec configureExecSpec(AboutToolkitExecSpec execSpec) {
    return null
  }

  @Override
  protected AboutToolkitExtension getToolExtension() {
    return null
  }
}
