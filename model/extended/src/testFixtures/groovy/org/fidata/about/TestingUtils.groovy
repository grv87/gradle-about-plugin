// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about

import com.google.common.io.Resources
import java.lang.reflect.Method

final class TestingUtils {
  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: tests/testing_utils.py#get_test_loc
   * SnippetCopyrightText: (c) 2017 nexB Inc. http://www.nexb.com/ - All rights reserved.
   * LicenseInfoInSnippet: Apache-2.0
   */
  /**
   * Returns the location of a test file or directory given a path relative to
   * the testdata directory
   *
   * @param path
   * @param must_exists
   * @return
   */
  // @third party code - END aboutcode-toolkit
  static File getTestLoc(String path, boolean mustExists = true) {
    File testLoc = new File(Resources.getResource(this, path).toURI())
    if (mustExists) {
      assert testLoc.exists()
    }
    testLoc
  }

  static List<String> getListSetProperties(Class aClass) {
    aClass.declaredMethods.find { Method method ->
      method.name ==~ /^get[A-Z]/ && method.parameterCount == 0 && (Set.isAssignableFrom(method.returnType) || List.isAssignableFrom(method.returnType))
    }.collect { Method method ->
      method.name.substring(3).uncapitalize()
    }
  }

  private TestingUtils() {
    throw new UnsupportedOperationException()
  }
}