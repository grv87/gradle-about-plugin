// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.extended.ExtendedAbout

@CompileStatic
final class ExtendedAboutPlugin extends AbstractAboutPlugin<ExtendedAbout> {
  @Override
  protected ExtendedAbout readFromFile(File src) {
    ExtendedAbout.readFromFile(src)
  }
}
