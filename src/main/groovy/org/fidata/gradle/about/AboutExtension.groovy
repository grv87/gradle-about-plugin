package org.fidata.gradle.about

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.yaml.snakeyaml.Yaml
import javax.inject.Inject

@CompileStatic
final class AboutExtension {
  private final Project project

  private static final Yaml YAML = new Yaml()

  private final Map<String, Object> about

  final String description
  final List<String> xKeywords
  final URL downloadUrl
  final URL homepageUrl
  final File changelogFile
  final String notes
  final String owner
  final URL ownerUrl
  final String contact
  final String author
  final URL authorUrl
  final String copyright
  final File noticeFile
  final URL noticeUrl
  final File licenseFile
  final URL licenseUrl
  final AnyLicenseInfo licenseExpression
  final String licenseName
  final String licenseKey
  final boolean redistribute
  final boolean attribute
  final boolean trackChanges
  final boolean modified
  final boolean internalUseOnly
  final String vcsTool
  final String vcsRepository
  final String vcsPath
  final String vcsTag
  final String vcsBranch
  final String Revision
  final byte[] checksumMd5
  final byte[] checksumSha1

  String getStringValue(String key) {
    about[key].toString()
  }

  URL getUrlValue(String key) {
    new URL(getStringValue(key))
  }

  @Inject
  AboutExtension(final Project project) {
    this.@project = project

    File aboutFile = project.file(project.name + ".ABOUT")
    about = aboutFile.withReader('US-ASCII'/*Charsets.US_ASCII*/) { Reader reader ->
      YAML.loadAs(reader, Map<String, Object>)
    }
  }
}
