// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AbstractAboutPlugin
import org.fidata.about.model.jvm.maven.MavenAbout

@CompileStatic
class MavenAboutPlugin extends AbstractAboutPlugin<MavenAbout> {

  @Override
  protected MavenAbout readFromFile(File src) {
    MavenAbout.readFromFile(src)
  }
}
