// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.extended

import groovy.transform.CompileStatic
import org.fidata.about.gradle.AbstractAboutPlugin
import org.fidata.about.model.extended.ExtendedAbout

@CompileStatic
final class ExtendedAboutPlugin extends AbstractAboutPlugin<ExtendedAbout> {
  @Override
  protected ExtendedAbout readFromFile() {
    ExtendedAbout.readFromFile(aboutFile)
  }
}
