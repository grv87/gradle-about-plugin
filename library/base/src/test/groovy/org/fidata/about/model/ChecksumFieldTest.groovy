// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import org.junit.Test

class ChecksumFieldTest {
  @Test
  void testToString() {
    final byte[] value = [1, 2, 3, 423434324] // TODO: .toArray(new byte[4])
    final ChecksumField field = new ChecksumField(value)
    final String valueToString = ChecksumField.CHECKSUM_ENCODING.encode(value)
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
