package org.fidata.about.model

import org.junit.Test

class StringFieldTest {
  @Test
  void testToString() {
    final String value = 'foo'
    final StringField field = new StringField(value)
    final String fieldToString = field.toString()
    assert fieldToString.contains(value)
  }
}
