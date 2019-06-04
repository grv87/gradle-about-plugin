/**
 * ============================================================================
 *  Copyright (c) 2017 nexB Inc. http://www.nexb.com/ - All rights reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============================================================================
 */
package org.fidata.about

import com.google.common.io.Resources
import java.lang.reflect.Method

final class TestingUtils {
  /**
   * Returns the location of a test file or directory given a path relative to
   * the testdata directory
   *
   * @param path
   * @param must_exists
   * @return
   */
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