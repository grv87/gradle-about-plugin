// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.model.maven.MavenAbout

@CompileStatic
class MavenAboutPlugin extends AbstractAboutPlugin<MavenAbout> {

  @Override
  protected MavenAbout readFromFile(File src) {
    MavenAbout.readFromFile(src)
  }
}
