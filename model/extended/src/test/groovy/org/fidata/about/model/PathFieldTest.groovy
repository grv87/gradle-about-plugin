// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import static org.fidata.about.TestingUtils.getTestLoc
import java.nio.file.Path
import org.fidata.utils.PathAbsolutizer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class PathFieldTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: tests/testing_utils.py#get_test_loc
   * SnippetCopyrightText: (c) 2017 nexB Inc. http://www.nexb.com/
   * LicenseInfoInSnippet: Apache-2.0
   */
  @Test
  void testCheckLocation() {
    String testFile = 'license.LICENSE'
    File baseDir = getTestLoc('model/base_dir')

    PathAbsolutizer pathAbsolutizer = new PathAbsolutizer(baseDir)

    PathField field = new PathField(pathAbsolutizer, testFile)
    /* TODO
    errors = field.validate(base_dir = base_dir)
    expected_errrors = []
    assert expected_errrors == errors

    result = field.value[test_file]
    expected = add_unc(posixpath.join(to_posix(base_dir), test_file))
    assert expected == result
    */
  }

  @Test
  void testCheckMissingLocation() {
    String testFile = 'does.not.exist'
    File baseDir = getTestLoc('model/base_dir')

    PathAbsolutizer pathAbsolutizer = new PathAbsolutizer(baseDir)

    String filePath = new File(baseDir, testFile).toString()
    String errMsg = String.format('Path %s not found', filePath)

    thrown.expect IllegalArgumentException
    thrown.expectMessage errMsg

    PathField field = new PathField(pathAbsolutizer, testFile)
  }
  // @third party code - END aboutcode-toolkit

  @Test
  void testToString() {
    final String testFile = 'license.LICENSE'
    final File baseDir = getTestLoc('model/base_dir')

    final PathAbsolutizer pathAbsolutizer = new PathAbsolutizer(baseDir)

    final PathField field = new PathField(pathAbsolutizer, testFile)

    final Path value = field.value
    final String valueToString = value.toString()
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
