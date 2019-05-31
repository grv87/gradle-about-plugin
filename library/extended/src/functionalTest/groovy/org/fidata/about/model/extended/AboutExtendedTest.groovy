package org.fidata.about.model.extended

import static org.fidata.about.TestingUtils.getTestLoc
import org.fidata.about.extended.AboutExtended
import org.fidata.about.extended.maven.LicenseExtended
import org.fidata.about.model.License
import org.junit.Test

class AboutExtendedTest {
  @Test
  void testIsAbleToReadAboutExtended() {
    final File testFile = getTestLoc('model/extended/extended.ABOUT')
    final AboutExtended a = AboutExtended.readFromFile(testFile)
    assert a.versioningSchema.value == 'semver-2.0.0'
    assert a.inceptionYear.value == '2019, very good year'

    final License l = a.licenses[0]
    assert LicenseExtended.isInstance(l)
    final LicenseExtended le = (LicenseExtended)l
    assert le.comments.value == 'permissive license'
    assert le.distribution.value == 'repo'

    assert a.organization.name.value == 'Example Inc.'
    assert a.organization.url.value == new URL('https://example.com/')

    assert a.developers.size() == 1
    assert a.developers[0].id.value == 'john_doe'
    assert a.developers[0].name.value == 'John Doe'
    assert a.developers[0].organization.value == 'Example Inc.'
    assert a.developers[0].roles.size() == 2
    assert a.developers[0].roles[0].value == 'owner'
    assert a.developers[0].roles[0].value == 'developer'
    assert a.developers[0].timezone.value == 'America/Argentina/Mendoza'
    assert a.developers[0].properties.size() == 1
    assert a.developers[0].properties['custom_property'].value == 'custom_property_value'

    assert a.issueManagement.system.value == 'Example BugTracker'
    assert a.issueManagement.url.value == new URL('https://bugs.example.com/extended/')

    assert a.ciManagement.system.value == 'ExampleCI'
    assert a.ciManagement.url.value == new URL('https://ci.example.com/extended/')

    assert a.mailingLists.size() == 1
    assert a.mailingLists[0].subscribe.value == 'subsribe@maillist.example.com'
    assert a.mailingLists[0].unsubscribe.value == 'unsubsribe@maillist.example.com'
    assert a.mailingLists[0].post.value == 'post@maillist.example.com'
    assert a.mailingLists[0].archiveUrl.value == new URL('https://maillist.example.com/archive')

    assert a.keywords.size() == 3
    assert a.keywords[0].value == 'extended'
    assert a.keywords[0].value == 'expanded'
    assert a.keywords[0].value == 'example'
  }
}
