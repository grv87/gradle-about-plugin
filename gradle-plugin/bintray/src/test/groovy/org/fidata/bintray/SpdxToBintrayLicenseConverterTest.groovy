// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.bintray

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.spdx.rdfparser.license.AnyLicenseInfo
import org.spdx.rdfparser.license.LicenseInfoFactory

@RunWith(JUnitParamsRunner)
class SpdxToBintrayLicenseConverterTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  @Test
  @Parameters
  @TestCaseName('convert({0}) succeeds')
  testConvertSucceeds(String spdxLicenseId, List<String> expected) {
    AnyLicenseInfo licenseInfo = LicenseInfoFactory.parseSPDXLicenseString(spdxLicenseId)
    List<String> result = SpdxToBintrayLicenseConverter.convert(licenseInfo)
    assert expected.sort() == result.toSorted()
  }

  Object[] parametersForTestConvertSucceeds() {
    Object[] result = [
      // Identical
      ['SimPL-2.0', ['SimPL-2.0']],
      // Mapping
      ['IPL-1.0', ['IBMPL-1.0']],
      // Conjunction
      ['Apache-2.0 OR MIT', ['Apache-2.0', 'MIT']],
      // Disjunction
      ['Apache-1.0 AND ImageMagick', ['Apache-1.0', 'ImageMagick']],
      // WithException operator
      ['GPL-2.0-only WITH Bootloader-exception', ['GPL-2.0']],
      // Special cases
      ['GPL-3.0-only', ['GPL-3.0-only']],
      ['GPL-3.0-or-later', ['GPL-3.0-or-later']],
      ['GPL-2.0-only WITH Classpath-exception-2.0', ['GPL-2.0+CE']],
      // Custom licenses
      ['Facebook-Platform', ['Facebook-Platform']],
      ['LicenseRef-custom', ['custom']],
    ]*.toArray().toArray()
    assert result.length > 0
    result
  }

  @Test
  @Parameters
  @TestCaseName('convert({0}) fails')
  testConvertFails(String spdxLicenseId) {
    AnyLicenseInfo licenseInfo = LicenseInfoFactory.parseSPDXLicenseString(spdxLicenseId)
    thrown.expect(IllegalArgumentException)
    SpdxToBintrayLicenseConverter.convert(licenseInfo)
  }

  Object[] parametersForTestConvertFails() {
    Object[] result = [
      // No mappings
      'LPPL-1.1',
      // Special SPDX cases
      'NONE',
      'NOASSERTION',
    ].toArray()
    assert result.length > 0
    result
  }
}
