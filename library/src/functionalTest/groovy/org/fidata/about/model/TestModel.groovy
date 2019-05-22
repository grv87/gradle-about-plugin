package org.fidata.about.model

import static org.fidata.about.TestingUtils.getTestLoc
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class TestModel {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  @Test
  void testAboutDuplicateFieldNamesAreDetectedWithDifferentCase() {
    File testFile = getTestLoc('model/parse/dupe_field_name.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)

    null
  }

  /*
  void test_About_hydrate_normalize_field_names_to_lowercase() {
    test_content = get_test_content('test_gen/parser_tests/upper_field_names.ABOUT')
    fields = saneyaml.load(test_content).items()
    a = About.readFromFile()
    for
    _ in range(3) {
      self.check_About_hydrate(a, fields)
    }
  }*/

  /*
  void test_About_with_existing_about_resource_has_no_error() {
    test_file = get_test_loc('test_gen/parser_tests/about_resource_field.ABOUT')
    a = About.readFromFile(test_file)
    assert [] == a.errors
    result = a.about_resource.value['about_resource.c']
    // this means we have a location
    self.assertNotEqual([], result)
  }*/

  void testAboutHasErrorsWhenAboutResourceIsMissing() {
    File testFile = getTestLoc('gen/parser_tests/.ABOUT')
    
    thrown.expect IOException
    
    About a = About.readFromFile(test_file)
  }

  @Test
  void testAboutHasErrorsWhenMissingRequiredFieldsAreMissing() {
    File testFile = getTestLoc('model/parse/missing_required.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

  @Test
  void testAboutHasErrorsWhenRequiredFieldsAreEmpty() {
    File testFile = getTestLoc('model/parse/empty_required.ABOUT')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

  @Test
  void testAboutHasErrorsWithEmptyNoticeFileField() {
    File testFile = getTestLoc('model/parse/empty_notice_field.about')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }

 /* void test_About_custom_fields_are_never_ignored() {
    test_file = get_test_loc('test_model/custom_fields/custom_fields.about')
    a = About.readFromFile(test_file)
    result = [(n, f.value )
    for
    n , f in a.custom_fields.items() ]
    expected = [
      (u 'single_line', u 'README STUFF' ),
      (u 'multi_line', u 'line1\nline2' ),
      (u 'other', u 'sasasas' ),
      (u 'empty', u '' )
    ]

    assert expected == result
  }

  void test_About_custom_fields_are_not_ignored_and_order_is_preserved() {
    test_file = get_test_loc('test_model/custom_fields/custom_fields.about')
    a = About.readFromFile(test_file)
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
  }

  void test_About_has_errors_for_illegal_custom_field_name() {
    test_file = get_test_loc('test_model/parse/illegal_custom_field.about')
    a = About.readFromFile(test_file)
    expected_errors = [
      Error(INFO, 'Field hydrate is a custom field.'),
      Error(CRITICAL, "Internal error with custom field { 'hydrate' { 'illegal name'.")
    ]
    assert expected_errors == a.errors
    assert not
    hasattr(getattr(a, 'hydrate'), 'value')
    field = list(a.custom_fields.values())[0]
    assert 'hydrate' == field.name
    assert 'illegal name' == field.value
  }

  void test_About_file_fields_are_empty_if_present_and_path_missing() {
    test_file = get_test_loc('test_model/parse/missing_notice_license_files.ABOUT')
    a = About.readFromFile(test_file)

    file_path1 = posixpath.join(posixpath.dirname(test_file), 'test.LICENSE')
    file_path2 = posixpath.join(posixpath.dirname(test_file), 'test.NOTICE')

    err_msg1 = Error(CRITICAL, 'Field license_file { Path %s not found' % file_path1)
    err_msg2 = Error(CRITICAL, 'Field notice_file { Path %s not found' % file_path2)

    expected_errors = [err_msg1, err_msg2]
    assert expected_errors == a.errors

    assert { 'test.LICENSE' : None } == a.license_file.value
    assert { 'test.NOTICE' : None } == a.notice_file.value
  }

  void test_About_license_and_notice_text_are_empty_if_field_missing() {
    test_file = get_test_loc('test_model/parse/no_file_fields.ABOUT')
    a = About.readFromFile(test_file)
    assert [] == a.errors
    assert {} == a.license_file.value
    assert {} == a.notice_file.value
  }

  void test_About_rejects_non_ascii_names_and_accepts_unicode_values() {
    test_file = get_test_loc('test_model/parse/non_ascii_field_name_value.about')
    a = About.readFromFile(test_file)
    expected = [
      Error(CRITICAL, "Field name: 'mat\\xedas' contains illegal name characters { 0 to 9, a to z, A to Z and _.")
    ]
    assert expected == a.errors
  }*/

  @Test
  void testAboutInvalidBooleanValue() {
    File testFile = getTestLoc('model/parse/invalid_boolean.about')

    thrown.expect IOException

    About a = About.readFromFile(testFile)
  }
/*
  void test_About_contains_about_file_path() {
    test_file = get_test_loc('model/serialize/about.ABOUT')

    thrown.expect IOException

    a = About.readFromFile(test_file)
  }*/

  @Test
  void testAboutEquals() {
    File testFile = getTestLoc('model/equal/complete/about.ABOUT')
    About a = About.readFromFile(testFile)
    About b = About.readFromFile(testFile)
    assert a == b
  }

  @Test
  void testAboutAreNotEqualWithSmallTextDifferences() {
    File testFile = getTestLoc('model/equal/complete2/about.ABOUT')
    About a = About.readFromFile(testFile)
    File testFile2 = getTestLoc('model/equal/complete/about.ABOUT')
    About b = About.readFromFile(testFile2)
    assert a != b
  }
}