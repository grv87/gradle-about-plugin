package org.fidata.about.model

import org.junit.Test
import org.spdx.rdfparser.license.AnyLicenseInfo
import org.spdx.rdfparser.license.LicenseInfoFactory

class LicenseExpressionFieldTest {
  @Test
  void testToString() {
    final AnyLicenseInfo value = LicenseInfoFactory.parseSPDXLicenseString('GPL-3.0')
    final LicenseExpressionField field = new LicenseExpressionField(value)
    final String valueToString = value.toString()
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
