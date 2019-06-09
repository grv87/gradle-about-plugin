// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model

import static org.fidata.about.TestingUtils.getTestLoc
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import java.nio.file.Path
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.hamcrest.CoreMatchers
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner)
class AboutTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: tests/test_model.py
   * SnippetCopyrightText: (c) 2017 nexB Inc. http://www.nexb.com/
   * LicenseInfoInSnippet: Apache-2.0
   */
  @Test
  void testDuplicateFieldNamesAreDetectedWithDifferentCase() {
    File testFile = getTestLoc('model/parse/dupe_field_name.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

  /*
  void test_hydrate_normalize_field_names_to_lowercase() {
    test_content = get_test_content('test_gen/parser_tests/upper_field_names.ABOUT')
    fields = saneyaml.load(test_content).items()
    a = About.readFromFile()
    for
    _ in range(3) {
      self.check_About_hydrate(a, fields)
    }
  }*/

  @Test
  void testWithExistingAboutResourceHasNoError() {
    File testFile = getTestLoc('gen/parser_tests/about_resource_field.ABOUT')
    About a = About.readFromFile(testFile)
    Path result = a.aboutResource.value
    // this means we have a location
    assert result && !result.empty
  }

  @Test
  void testHasErrorsWhenAboutResourceIsMissing() {
    File testFile = getTestLoc('gen/parser_tests/.ABOUT')
    
    thrown.expect IOException
    
    About a = About.readFromFile(testFile)
  }

  @Test
  void testHasErrorsWhenMissingRequiredFieldsAreMissing() {
    File testFile = getTestLoc('model/parse/missing_required.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

  @Test
  void testHasErrorsWhenRequiredFieldsAreEmpty() {
    File testFile = getTestLoc('model/parse/empty_required.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

  @Ignore('https://github.com/FasterXML/jackson-databind/issues/2024')
  @Test
  void testHasErrorsWithEmptyNoticeFileField() {
    File testFile = getTestLoc('model/parse/empty_notice_field.about')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
    println a.noticeFile.value
  }

  @Test
  void testCustomFieldsAreNeverIgnored() {
    File testFile = getTestLoc('model/custom_fields/custom_fields.about')
    About a = About.readFromFile(testFile)
    Map<String, String> result = a.customFields.collectEntries { [(it.key): (String)it.value.value] }
    Map<String, String> expected = [
      'single_line': 'README STUFF',
      'multi_line': 'line1\nline2',
      'other': 'sasasas',
      'empty': ''
    ]
    assert expected == result
  }
/*
  void testCustomFieldsAreNotIgnoredAndOrderIsPreserved() {
    About testFile = getTestLoc('model/custom_fields/custom_fields.about')
    a = About.readFromFile(testFile)
    result = [(n, f.value )
    for
    n , f in a.custom_fields.items() ]
    expected = [
      (u 'single_line', u 'README STUFF' ),
      (u 'multi_line', u 'line1\nline2' ),
      (u 'other', u 'sasasas' ),
      (u 'empty', u '' )
    ]
    assert sorted(expected) == sorted(result)
  }*/

/*
  void testFileFieldsAreEmptyIfPresentAndPathMissing() {
    About testFile = getTestLoc('model/parse/missing_notice_license_files.ABOUT')
    a = About.readFromFile(testFile)

    file_path1 = posixpath.join(posixpath.dirname(testFile), 'test.LICENSE')
    file_path2 = posixpath.join(posixpath.dirname(testFile), 'test.NOTICE')

    err_msg1 = Error(CRITICAL, 'Field license_file { Path %s not found' % file_path1)
    err_msg2 = Error(CRITICAL, 'Field notice_file { Path %s not found' % file_path2)

    expected_errors = [err_msg1, err_msg2]
    assert expected_errors == a.errors

    assert { 'test.LICENSE' : None } == a.license_file.value
    assert { 'test.NOTICE' : None } == a.notice_file.value
  }

  void testLicenseAndNoticeTextAreEmptyIfFieldMissing() {
    About testFile = getTestLoc('model/parse/no_file_fields.ABOUT')
    a = About.readFromFile(testFile)
    assert [] == a.errors
    assert {} == a.license_file.value
    assert {} == a.notice_file.value
  }*/

  @Test
  void testRejectsNonAsciiNamesAndAcceptsUnicodeValues() {
    File testFile = getTestLoc('model/parse/non_ascii_field_name_value.about')

    thrown.expect IOException
    thrown.expectMessage "Field name: 'mat\u00edas' contains illegal name characters: 0 to 9, a to z, A to Z and _."

    About a = About.readFromFile(testFile)
  }

  @Test
  void testInvalidBooleanValue() {
    File testFile = getTestLoc('model/parse/invalid_boolean.about')

    thrown.expect IOException
    thrown.expectMessage CoreMatchers.containsString("Invalid flag value: 'blah'")

    About a = About.readFromFile(testFile)
  }
/*
  void testContainsAboutFilePath() {
    About testFile = getTestLoc('model/serialize/about.ABOUT')

    thrown.expect IOException

    a = About.readFromFile(testFile)
  }*/

  @Test
  void testEquals() {
    File testFile = getTestLoc('model/equal/complete/about.ABOUT')
    About a = About.readFromFile(testFile)
    About b = About.readFromFile(testFile)
    assert a == b
  }

  @Test
  void testAreNotEqualWithSmallTextDifferences() {
    File testFile = getTestLoc('model/equal/complete2/about.ABOUT')
    About a = About.readFromFile(testFile)
    File testFile2 = getTestLoc('model/equal/complete/about.ABOUT')
    About b = About.readFromFile(testFile2)
    assert a != b
  }
  // @third party code - END aboutcode-toolkit

  @Test
  void testInitsAllFieldsToNotNull() {
    File testFile = getTestLoc('gen/parser_tests/about_resource_field.ABOUT')
    About a = About.readFromFile(testFile)
    [
      'aboutResource',
      'name',
      'version',
      'specVersion',
      'description',
      'downloadUrl',
      'homepageUrl',
      'changelogFile',
      'notes',
      'owner',
      'ownerUrl',
      'contact',
      'author',
      'authorFile',
      'copyright',
      'noticeFile',
      'noticeUrl',
      'licenses',
      'licenseExpression',
      'redistribute',
      'attribute',
      'trackChanges',
      'modified',
      'internalUseOnly',
      'vcsTool',
      'vcsRepository',
      'vcsPath',
      'vcsTag',
      'vcsBranch',
      'vcsRevision',
      'checksums',
    ].each { String it ->
      assert a."$it" != null
    }
  }

  @Test
  void testIsAbleToReadStructuredCustomFields() {
    File testFile = getTestLoc('model/custom_fields/structured_custom_fields.ABOUT')
    About a = About.readFromFile(testFile)
    println a
    /* TODO:
    Map<String, String> result = a.customFields.collectEntries { [(it.key): (String)it.value.value] }
    Map<String, String> expected = [
      'single_line': 'README STUFF',
      'multi_line': 'line1\nline2',
      'other': 'sasasas',
      'empty': ''
    ]
    assert expected == result*/
  }

  @Test
  void testSetsDefaultLicenseExpressionToNone() {
    File testFile = getTestLoc('model/license_expression/default.about')
    About a = About.readFromFile(testFile)
    assert a.licenseExpression.value.toString() == 'NONE'
  }

  @Test
  void testAcceptsValidLicenseExpression() {
    File testFile = getTestLoc('model/license_expression/valid.about')
    About a = About.readFromFile(testFile)
  }

  @Test
  void testAcceptsLicenseExpressionWithException() {
    File testFile = getTestLoc('model/license_expression/with_exception.about')
    About a = About.readFromFile(testFile)
  }

  @Test
  void testDoesntAcceptEmptyLicenseExpression() {
    File testFile = getTestLoc('model/license_expression/empty.about')
    thrown.expect IOException
    About a = About.readFromFile(testFile)
  }

  @Test
  void testDoesntAcceptNullLicenseExpression() {
    File testFile = getTestLoc('model/license_expression/null.about')
    thrown.expect IOException
    About a = About.readFromFile(testFile)
  }

  @Test
  void testDoesntAcceptInvalidLicenseExpression() {
    File testFile = getTestLoc('model/license_expression/invalid.about')
    thrown.expect IOException
    About a = About.readFromFile(testFile)
  }

  @Test
  @Parameters
  @TestCaseName('{0} is immutable')
  void testAllCollectionsAreReadOnly(String fieldName, @ClosureParams(value = SimpleType, options = "org.fidata.about.model.About") Closure closure) {
    File testFile = getTestLoc('model/custom_fields/structured_custom_fields.ABOUT')
    About a = About.readFromFile(testFile)

    thrown.expect(UnsupportedOperationException)
    closure.call(a)
  }

  Object[] parametersForTestAllCollectionsAreReadOnly() {
    Object[] result = [
      [
        'licenses',
        { it.licenses.add(null) }
      ],
      [
        'checksums',
        { it.checksums['abc'] = ChecksumField.NULL }
      ],
      [
        'customFields',
        { it.customFields['def'] = Boolean.TRUE }
      ],
      [
        'custom_list',
        { it.customFields['custom_list'].add 'qwe' }
      ],
      [
        'custom_hash',
        { it.customFields['custom_hash']['ghi'] = 'rst' }
      ],
    ]*.toArray().toArray()
    assert result.length > 0
    result
  }

  @Test
  @Parameters
  @TestCaseName('testInitsAllLicenseFieldsToNotNull({0})')
  void testInitsAllLicenseFieldsToNotNull(String testName) {
    File testFile = getTestLoc("model/license_${ testName }.ABOUT")
    About a = About.readFromFile(testFile)
    License license = a.licenses[0]
    [
      'key',
      'name',
      'files',
      'url',
    ].each { String it ->
      assert license."$it" != null
    }
    assert license."$testName".value == 'Apache-2.0'
  }

  Object[] parametersForTestInitsAllLicenseFieldsToNotNull() {
    Object[] result = [
      [
        'key',
      ],
      [
        'name',
      ],
    ]*.toArray().toArray()
    assert result.length > 0
    result
  }

  @Test
  void testAcceptsSeveralLicenseFiles() {
    File testFile = getTestLoc('model/several_license_files/several_license_files.ABOUT')
    About a = About.readFromFile(testFile)
  }
}
