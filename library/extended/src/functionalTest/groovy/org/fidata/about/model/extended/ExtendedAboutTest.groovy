package org.fidata.about.model.extended

import static org.fidata.about.TestingUtils.getTestLoc
import org.fidata.about.extended.ExtendedAbout
import org.junit.Test

class ExtendedAboutTest {
  @Test
  void testIsAbleToReadExtendedAbout() {
    final File testFile = getTestLoc('extended/extended.ABOUT')
    final ExtendedAbout a = ExtendedAbout.readFromFile(testFile)

    assert a.versioningSchema.value == 'semver-2.0.0'

    assert a.keywords.size() == 3
    assert a.keywords[0].value == 'extended'
    assert a.keywords[1].value == 'expanded'
    assert a.keywords[2].value == 'example'
  }
}
