// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import org.junit.Test

class BooleanFieldTest {
  @Test
  void testToString() {
    final boolean value = true
    final BooleanField field = new BooleanField(value)
    final String valueToString = value.toString()
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
