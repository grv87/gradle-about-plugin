package org.fidata.about.model

import org.junit.Test

class UrlFieldTest {
  private static boolean isValidUrl(String value) {
    try {
      new UrlField(value)
      return true
    } catch (MalformedURLException e) {
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
    assert isValidUrl('http://de.wikipedia.org/wiki/Elf (Begriffsklärung)')
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
    /*
     * CAVEAT:
     * Here is a bug in aboutcode-toolkit.
     * Empty path is legal according to the section 3 of RFC 3986.
     * <grv87 2019-05-24>
     */
    assert isValidUrl('http:')
  }
}
