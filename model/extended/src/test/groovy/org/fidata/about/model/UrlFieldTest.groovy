// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import org.junit.Test

class UrlFieldTest {
  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: tests/test_model.py
   * SnippetCopyrightText: (c) 2014-2018 nexB Inc. http://www.nexb.com/ - All rights reserved.
   * LicenseInfoInSnippet: Apache-2.0
   */
  private static boolean isValidUrl(String value) {
    try {
      new UrlField(value)
      return true
    } catch (URISyntaxException | IllegalArgumentException e) {
      return false
    }
  }

  @Test
  void testIsValidUrl() {
    assert isValidUrl('http://www.google.com')
  }

  @Test
  void testIsValidUrlNotStartingWithWww() {
    assert isValidUrl('https://nexb.com')
    assert isValidUrl('http://archive.apache.org/dist/httpcomponents/commons-httpclient/2.0/source/commons-httpclient-2.0-alpha2-src.tar.gz')
    /*
     * CAVEAT:
     * Here aboutcode-toolkit tests URL with space.
     * We don't support it. Such URL would be incorrect
     * <grv87 2019-06-03>
     */
    assert isValidUrl('http://de.wikipedia.org/wiki/Elf_(Begriffsklärung)')
    assert isValidUrl('http://nothing_here.com')
  }

  @Test
  void testIsValidUrlNoSchemes() {
    assert !isValidUrl('google.com')
    assert !isValidUrl('www.google.com')
    assert !isValidUrl('')
  }

  @Test
  void testIsValidUrlNotEndsWithCom() {
    assert isValidUrl('http://www.google')
  }
  @Test
  void testIsValidUrlEndsWithSlash() {
    assert isValidUrl('http://www.google.co.uk/')
  }

  @Test
  void testIsValidUrlEmptyUrl() {
    assert !isValidUrl('http:')
  }
  // @third party code - END aboutcode-toolkit

  @Test
  void testToString() {
    final URI value = new URI('https://fidata.org/')
    final UrlField field = new UrlField(value)
    final String valueToString = value.toString()
    final String fieldToString = field.toString()
    assert fieldToString.contains(valueToString)
  }
}
