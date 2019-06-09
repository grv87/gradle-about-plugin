// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import static org.fidata.about.TestingUtils.getTestLoc
import java.nio.file.Path
import org.fidata.utils.PathAbsolutizer
import org.junit.Test

class AboutResourceFieldTest {
  @Test
  void testToString() {
    final String testFile = 'license.LICENSE'
    final File baseDir = getTestLoc('model/base_dir')

    final PathAbsolutizer pathAbsolutizer = new PathAbsolutizer(baseDir)

    final AboutResourceField field = new AboutResourceField(pathAbsolutizer, testFile)

    final Path value = field.value
    final String valueToString = value.toString()
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
