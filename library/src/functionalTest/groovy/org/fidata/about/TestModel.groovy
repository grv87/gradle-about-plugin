package org.fidata.about

import static org.fidata.about.TestingUtils.getTestLoc
import org.fidata.about.model.About
import org.junit.Test

class TestModel {
  /*void test_About_load_ignores_original_field_order_and_uses_standard_predefined_order(self) {
    // fields in this file are not in the standard order
    test_file = get_test_loc('test_model/parse/ordered_fields.ABOUT')
    a = model.About(test_file)
    assert [] == a.errors

    expected = ['about_resource', 'name', 'version', 'download_url']
    result = [f.name
    for
    f in a.all_fields()
    if
    f.present ]
    assert expected == result
  }*/

  @Test(expected = Exception) // TODO
  void testAboutDuplicateFieldNamesAreDetectedWithDifferentCase(self) {
    // This test is failing because the YAML does not keep the order when
    // loads the test files. For instance, it treat the 'About_Resource' as the
    // first element and therefore the dup key is 'about_resource'.
    File testFile = getTestLoc('test_model/parse/dupe_field_name.ABOUT')

    a = About.readFromFile(testFile)
    // a = model.About(testFile)
    /* TODO
    expected = [
      Error(WARNING, 'Field About_Resource is a duplicate. Original value { "." replaced with { "new value"'),
      Error(WARNING, 'Field Name is a duplicate. Original value { "old" replaced with { "new"')
    ]

    result = a.errors
    assert sorted(expected) == sorted(result)*/
  }

  /*void test_About_duplicate_field_names_are_not_reported_if_same_value(self) {
    // This test is failing because the YAML does not keep the order when
    // loads the test files. For instance, it treat the 'About_Resource' as the
    // first element and therefore the dup key is 'about_resource'.
    test_file = get_test_loc('test_model/parse/dupe_field_name_no_new_value.ABOUT')
    a = model.About(test_file)
    expected = [
    ]
    result = a.errors
    assert sorted(expected) == sorted(result)
  }*/

  /*void check_About_hydrate(self, about, fields) {
    expected = set([
      'name',
      'homepage_url',
      'download_url',
      'version',
      'copyright',
      'date',
      'license_spdx',
      'license_text_file',
      'notice_file',
      'about_resource'])

    expected_errors = [
      Error(INFO, 'Field date is a custom field.'),
      Error(INFO, 'Field license_spdx is a custom field.'),
      Error(INFO, 'Field license_text_file is a custom field.')]

    errors = about.hydrate(fields)

    assert expected_errors == errors

    result = set([f.name
    for
    f in about.all_fields()
    if
    f.present ] )
    assert expected == result
  }

  void test_About_hydrate_normalize_field_names_to_lowercase(self) {
    test_content = get_test_content('test_gen/parser_tests/upper_field_names.ABOUT')
    fields = saneyaml.load(test_content).items()
    a = model.About()
    for
    _ in range(3) {
      self.check_About_hydrate(a, fields)
    }
  }*/

  /*void test_About_with_existing_about_resource_has_no_error(self) {
    test_file = get_test_loc('test_gen/parser_tests/about_resource_field.ABOUT')
    a = model.About(test_file)
    assert [] == a.errors
    result = a.about_resource.value['about_resource.c']
    // this means we have a location
    self.assertNotEqual([], result)
  }

  void test_About_has_errors_when_about_resource_is_missing(self) {
    test_file = get_test_loc('test_gen/parser_tests/.ABOUT')
    a = model.About(test_file)
    expected = [Error(CRITICAL, 'Field about_resource is required')]
    result = a.errors
    assert expected == result
  }

  void test_About_has_errors_when_about_resource_does_not_exist(self) {
    test_file = get_test_loc('test_gen/parser_tests/missing_about_ref.ABOUT')
    file_path = posixpath.join(posixpath.dirname(test_file), 'about_file_missing.c')
    a = model.About(test_file)
    err_msg = 'Field about_resource { Path %s not found' % file_path
    expected = [Error(INFO, err_msg)]
    result = a.errors
    assert expected == result

  }*/

  @Test(expected = Exception)
  void testAboutHasErrorsWhenMissingRequiredFieldsAreMissing(self) {
    File testFile = getTestLoc('test_model/parse/missing_required.ABOUT')
    About a = About.readFromFile(testFile)
    // model.About(test_file)
    /* TODO:
    expected = [
      Error(CRITICAL, 'Field about_resource is required'),
      Error(CRITICAL, 'Field name is required'),
    ]
    result = a.errors
    assert expected == result
    */
  }

  @Test(expected = Exception)
  void testAboutHasErrorsWhenRequiredFieldsAreEmpty(self) {
    File testFile = getTestLoc('test_model/parse/empty_required.ABOUT')
    About a = About.readFromFile(testFile)
    /* TODO:
    expected = [
      Error(CRITICAL, 'Field about_resource is required and empty'),
      Error(CRITICAL, 'Field name is required and empty'),
    ]
    result = a.errors
    assert expected == result
   */
  }

  @Test(expected = Exception)
  void test_About_has_errors_with_empty_notice_file_field(self) {
    File testFile = getTestLoc('test_model/parse/empty_notice_field.about')
    About a = About.readFromFile(testFile)
    /* TODO
    expected = [
      Error(INFO, 'Field notice_file is present but empty.')]
    result = a.errors
    assert expected == result
    */
  }

  void test_About_custom_fields_are_never_ignored(self) {
    test_file = get_test_loc('test_model/custom_fields/custom_fields.about')
    a = model.About(test_file)
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

  void test_About_custom_fields_are_not_ignored_and_order_is_preserved(self) {
    test_file = get_test_loc('test_model/custom_fields/custom_fields.about')
    a = model.About(test_file)
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

  void test_About_has_errors_for_illegal_custom_field_name(self) {
    test_file = get_test_loc('test_model/parse/illegal_custom_field.about')
    a = model.About(test_file)
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

  void test_About_file_fields_are_empty_if_present_and_path_missing(self) {
    test_file = get_test_loc('test_model/parse/missing_notice_license_files.ABOUT')
    a = model.About(test_file)

    file_path1 = posixpath.join(posixpath.dirname(test_file), 'test.LICENSE')
    file_path2 = posixpath.join(posixpath.dirname(test_file), 'test.NOTICE')

    err_msg1 = Error(CRITICAL, 'Field license_file { Path %s not found' % file_path1)
    err_msg2 = Error(CRITICAL, 'Field notice_file { Path %s not found' % file_path2)

    expected_errors = [err_msg1, err_msg2]
    assert expected_errors == a.errors

    assert { 'test.LICENSE' : None } == a.license_file.value
    assert { 'test.NOTICE' : None } == a.notice_file.value
  }

  void test_About_notice_and_license_text_are_loaded_from_file(self) {
    test_file = get_test_loc('test_model/parse/license_file_notice_file.ABOUT')
    a = model.About(test_file)

    expected = '''Tester holds the copyright for test component. Tester relinquishes copyright of
this software and releases the component to Public Domain.

* Email Test@tester.com for any questions'''

    result = a.license_file.value['license_text.LICENSE']
    assert expected == result

    expected = '''Test component is released to Public Domain.'''
    result = a.notice_file.value['notice_text.NOTICE']
    assert expected == result
  }

  void test_About_license_and_notice_text_are_empty_if_field_missing(self) {
    test_file = get_test_loc('test_model/parse/no_file_fields.ABOUT')
    a = model.About(test_file)
    assert [] == a.errors
    assert {} == a.license_file.value
    assert {} == a.notice_file.value
  }

  void test_About_rejects_non_ascii_names_and_accepts_unicode_values(self) {
    test_file = get_test_loc('test_model/parse/non_ascii_field_name_value.about')
    a = model.About(test_file)
    expected = [
      Error(CRITICAL, "Field name: 'mat\\xedas' contains illegal name characters { 0 to 9, a to z, A to Z and _.")
    ]
    assert expected == a.errors
  }

  void test_About_invalid_boolean_value(self) {
    test_file = get_test_loc('test_model/parse/invalid_boolean.about')
    a = model.About(test_file)
    expected_msg = "Field modified: Invalid flag value: 'blah'"
    assert expected_msg in a.errors[0].message
  }

  void test_About_contains_about_file_path(self) {
    test_file = get_test_loc('test_model/serialize/about.ABOUT')
    // TODO { I am not sure this override of the about_file_path makes sense
    a = model.About(test_file, about_file_path = 'complete/about.ABOUT')
    assert [] == a.errors
    expected = 'complete/about.ABOUT'
    result = a.about_file_path
    assert expected == result
  }

  void test_About_equals(self) {
    test_file = get_test_loc('test_model/equal/complete/about.ABOUT')
    a = model.About(test_file, about_file_path = 'complete/about.ABOUT')
    b = model.About(test_file, about_file_path = 'complete/about.ABOUT')
    assert a == b
  }

  void test_About_are_not_equal_with_small_text_differences(self) {
    test_file = get_test_loc('test_model/equal/complete2/about.ABOUT')
    a = model.About(test_file, about_file_path = 'complete2/about.ABOUT')
    test_file2 = get_test_loc('test_model/equal/complete/about.ABOUT')
    b = model.About(test_file2, about_file_path = 'complete/about.ABOUT')
    assert a.dumps() != b.dumps()
    assert a != b
  }

  void test_get_field_names_only_returns_non_empties(self) {
    a = model.About()
    a.custom_fields['f'] = model.StringField(
      name = 'f', value = '1', present = True)
    b = model.About()
    b.custom_fields['g'] = model.StringField(
      name = 'g', value = '1', present = True)
    abouts = [a, b]
    // ensure that custom fields and about file path are collected
    // and that all fields are in the correct order
    expected = [
      model.About.ABOUT_FILE_PATH_ATTR,
      'about_resource', 'name', 'f', 'g'
    ]
    result = model.get_field_names(abouts)
    assert expected == result
  }

  void test_get_field_names_does_not_return_duplicates_custom_fields(self) {
    a = model.About()
    a.custom_fields['f'] = model.StringField(name = 'f', value = '1',
      present = True)
    a.custom_fields['cf'] = model.StringField(name = 'cf', value = '1',
      present = True)
    b = model.About()
    b.custom_fields['g'] = model.StringField(name = 'g', value = '1',
      present = True)
    b.custom_fields['cf'] = model.StringField(name = 'cf', value = '2',
      present = True)
    abouts = [a, b]
    // ensure that custom fields and about file path are collected
    // and that all fields are in the correct order
    // FIXME { this is not USED
    expected = [
      'about_file_path',
      'about_resource',
      'name',
      'cf',
      'f',
      'g',
    ]
    result = model.get_field_names(abouts)
    assert expected == result
  }
}