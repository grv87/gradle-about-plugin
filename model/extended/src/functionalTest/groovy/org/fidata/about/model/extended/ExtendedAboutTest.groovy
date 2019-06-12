// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.extended

import static org.fidata.about.TestingUtils.getTestLoc
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.fidata.about.model.StringField
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner)
class ExtendedAboutTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  @Test
  void testIsAbleToReadExtendedAbout() {
    final File testFile = getTestLoc('model/extended/extended.ABOUT')
    final ExtendedAbout a = ExtendedAbout.readFromFile(testFile)

    assert a.versioningSchema.value == 'semver-2.0.0'

    assert a.keywords.size() == 3
    assert a.keywords[0].value == 'extended'
    assert a.keywords[1].value == 'expanded'
    assert a.keywords[2].value == 'example'
  }

  @Test
  @Parameters
  @TestCaseName('{0} is immutable')
  void testAllCollectionsAreReadOnly(String fieldName, @ClosureParams(value = SimpleType, options = "org.fidata.about.model.extended.ExtendedAbout") Closure closure) {
    File testFile = getTestLoc('model/extended/extended.ABOUT')
    ExtendedAbout a = ExtendedAbout.readFromFile(testFile)

    thrown.expect(UnsupportedOperationException)
    closure.call(a)
  }

  Object[] parametersForTestAllCollectionsAreReadOnly() {
    Object[] result = [
      [
        'keywords',
        { it.keywords.add(StringField.NULL) }
      ],
    ]*.toArray().toArray()
    assert result.length > 0
    result
  }
}
