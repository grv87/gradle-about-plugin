package org.fidata.gradle.about

import com.fasterxml.jackson.annotation.JacksonInject
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import javax.inject.Inject
import org.gradle.api.Project
import org.spdx.rdfparser.license.AnyLicenseInfo

// @Builder(builderStrategy = DefaultStrategy, prefix = 'with')
@JsonDeserialize(builder = AboutBuilder)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy)
@TupleConstructor
@CompileStatic
final class About {
  @JsonIgnore
  @Inject
  private final Project project

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
  final String revision
  final byte[] checksumMd5
  final byte[] checksumSha1

  final Map<String, Object> extValues

  @JsonAnyGetter
  Map<String, Object> getExtValues() {
    this.@extValues
  }

  // @Builder(builderStrategy = ExternalStrategy, forClass = About, prefix = 'with')
  final static class AboutBuilder {
    // @Inject
    private final Project project

    // @Inject
    AboutBuilder(@JacksonInject Project project) {
      this.project = project
    }

    private String description
    AboutBuilder withDescription(String description) {
      this.@description = description
      this
    }

    private List<String> xKeywords
    AboutBuilder withXKeywords(List<String> xKeywords) {
      this.@xKeywords = xKeywords
      this
    }

    private URL downloadUrl
    AboutBuilder withDownloadUrl(URL downloadUrl) {
      this.@downloadUrl = downloadUrl
      this
    }

    private URL homepageUrl
    AboutBuilder withHomepageUrl(URL homepageUrl) {
      this.@homepageUrl = homepageUrl
      this
    }

    private File changelogFile
    AboutBuilder withChangelogFile(File changelogFile) {
      this.@changelogFile = changelogFile
      this
    }

    private String notes
    AboutBuilder withNotes(String notes) {
      this.@notes = notes
      this
    }

    private String owner
    AboutBuilder withOwner(String owner) {
      this.@owner = owner
      this
    }

    private URL ownerUrl
    AboutBuilder withOwnerUrl(URL ownerUrl) {
      this.@ownerUrl = ownerUrl
      this
    }

    private String contact
    AboutBuilder withContact(String contact) {
      this.@contact = contact
      this
    }

    private String author
    AboutBuilder withAuthor(String author) {
      this.@author = author
      this
    }

    private URL authorUrl
    AboutBuilder withAuthorUrl(URL authorUrl) {
      this.@authorUrl = authorUrl
      this
    }

    private String copyright
    AboutBuilder withCopyright(String copyright) {
      this.@copyright = copyright
      this
    }

    private File noticeFile
    AboutBuilder withNoticeFile(File noticeFile) {
      this.@noticeFile = noticeFile
      this
    }

    private URL noticeUrl
    AboutBuilder withNoticeUrl(URL noticeUrl) {
      this.@noticeUrl = noticeUrl
      this
    }

    private File licenseFile
    AboutBuilder withLicenseFile(File licenseFile) {
      this.@licenseFile = licenseFile
      this
    }

    private URL licenseUrl
    AboutBuilder withLicenseUrl(URL licenseUrl) {
      this.@licenseUrl = licenseUrl
      this
    }

    private AnyLicenseInfo licenseExpression
    AboutBuilder withLicenseExpression(AnyLicenseInfo licenseExpression) {
      this.@licenseExpression = licenseExpression
      this
    }

    private String licenseName
    AboutBuilder withLicenseName(String licenseName) {
      this.@licenseName = licenseName
      this
    }

    private String licenseKey
    AboutBuilder withLicenseKey(String licenseKey) {
      this.@licenseKey = licenseKey
      this
    }

    private boolean redistribute
    AboutBuilder withRedistribute(boolean redistribute) {
      this.@redistribute = redistribute
      this
    }

    private boolean attribute
    AboutBuilder withAttribute(boolean attribute) {
      this.@attribute = attribute
      this
    }

    private boolean trackChanges
    AboutBuilder withTrackChanges(boolean trackChanges) {
      this.@trackChanges = trackChanges
      this
    }

    private boolean modified
    AboutBuilder withModified(boolean modified) {
      this.@modified = modified
      this
    }

    private boolean internalUseOnly
    AboutBuilder withDescription(boolean internalUseOnly) {
      this.@internalUseOnly = internalUseOnly
      this
    }

    private String vcsTool
    AboutBuilder withVcsTool(String vcsTool) {
      this.@vcsTool = vcsTool
      this
    }

    private String vcsRepository
    AboutBuilder withVcsRepository(String vcsRepository) {
      this.@vcsRepository = vcsRepository
      this
    }

    private String vcsPath
    AboutBuilder withVcsPath(String vcsPath) {
      this.@vcsPath = vcsPath
      this
    }

    private String vcsTag
    AboutBuilder withVcsTag(String vcsTag) {
      this.@vcsTag = vcsTag
      this
    }

    private String vcsBranch
    AboutBuilder withVcsBranch(String vcsBranch) {
      this.@vcsBranch = vcsBranch
      this
    }

    private String revision
    AboutBuilder withRevision(String revision) {
      this.@revision = revision
      this
    }

    private byte[] checksumMd5
    AboutBuilder withChecksumMd5(byte[] checksumMd5) {
      this.@checksumMd5 = checksumMd5
      this
    }

    private byte[] checksumSha1
    AboutBuilder withChecksumSha1(byte[] checksumSha1) {
      this.@checksumSha1 = checksumSha1
      this
    }

    private Map<String, Object> extValues = [:]

    @JsonAnySetter
    AboutBuilder withExtValue(String key, Object value) {
      this.@extValues[key] = value
      this
    }

    About build() {
      new About(
        description,
        xKeywords,
        downloadUrl,
        homepageUrl,
        changelogFile,
        notes,
        owner,
        ownerUrl,
        contact,
        author,
        authorUrl,
        copyright,
        noticeFile,
        noticeUrl,
        licenseFile,
        licenseUrl,
        licenseExpression,
        licenseName,
        licenseKey,
        redistribute,
        attribute,
        trackChanges,
        modified,
        internalUseOnly,
        vcsTool,
        vcsRepository,
        vcsPath,
        vcsTag,
        vcsBranch,
        revision,
        checksumMd5,
        checksumSha1,
        extValues,
      )
    }
  }
}
