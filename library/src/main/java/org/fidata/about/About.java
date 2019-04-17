package org.fidata.about;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.fidata.jackson.ChecksumDeserializer;
import org.fidata.jackson.ChecksumSerializer;
import org.fidata.jackson.RelativePathDeserializer;
import org.fidata.jackson.RelativePathSerializer;
import org.fidata.jackson.SpdxAnyLicenseInfoDeserializer;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import java.nio.file.Path;

@JsonDeserialize(builder = About.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "Builder")
final class About {
  /**
   * Component description, as a short text
   */
  @Getter
  private final String description;

  @Getter
  private final List<String> xKeywords;

  /**
   * A direct URL to download the original file or archive documented by this ABOUT file
   */
  @Getter
  private final URL downloadUrl;

  /**
   * URL to the homepage for this component
   */
  @Getter
  private final URL homepageUrl;

  /**
   * Changelog file for the component
   */
  @Getter
  private final Path changelogFile;

  /**
   * Notes and comments about the component
   */
  @Getter
  private final String notes;

  /**
   * The name of the primary organization or person(s) that owns or provides the component
   */
  @Getter
  private final String owner;

  /**
   * URL to the homepage for the owner
   */
  @Getter
  private final URL ownerUrl;

  /**
   * Contact information (such as an email address or physical address) for the component owner
   */
  @Getter
  private final String contact;

  /**
   * Name of the organization(s) or person(s) that authored the component
   */
  @Getter
  private final String author;

  /**
   * Author file for the component
   */
  @Getter
  private final Path authorFile;

  /**
   * Copyright statement for the component
   */
  @Getter
  private final String copyright;

  /**
   * Legal notice or credits for the component
   */
  @Getter
  private final Path noticeFile;

  /**
   * URL to a legal notice for the component
   */
  @Getter
  private final URL noticeUrl;

  /**
   * License file that applies to the component
   *
   * For example, the name of a license file such as LICENSE or COPYING file extracted from a downloaded archive
   */
  @Getter
  private final Path licenseFile;

  /**
   * URL to the license text for the component
   */
  @Getter
  private final URL licenseUrl;

  /**
   * The license expression that apply to the component
   *
   * You can separate each identifier using " or " and " and " to document the relationship
   * between multiple license identifiers, such as a choice among multiple licenses
   */
  @Getter
  private final AnyLicenseInfo licenseExpression;

  /**
   * The license short name for the license
   */
  @Getter
  private final String licenseName;

  /**
   * The license key(s) for the component
   */
  @Getter
  private final String licenseKey;

  /**
   * Set this flag to yes if the component license requires source code redistribution
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final boolean redistribute = false;

  /**
   * Set this flag to yes if the component license requires publishing an attribution or credit notice
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final boolean attribute = false;

  /**
   * Set this flag to yes if the component license requires tracking changes made to a the component
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final boolean trackChanges = false;

  /**
   * Set this flag to yes if the component has been modified
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final boolean modified = false;

  /**
   * Set this flag to yes if the component is used internal only
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final boolean internalUseOnly = false;

  /**
   * VCS tool such as git, svn, cvs, etc.
   */
  @Getter
  private final String vcsTool;

  /**
   * Typically a URL or some other identifier used by a VCS tool
   * to point to a repository such as an SVN or Git repository URL
   */
  @Getter
  private final String vcsRepository;

  /**
   * Path used by a particular VCS tool to point to a file, directory or module inside a repository
   */
  @Getter
  private final String vcsPath;

  /**
   * Tag name or path used by a particular VCS tool
   */
  @Getter
  private final String vcsTag;

  /**
   * Branch name or path used by a particular VCS tool
   */
  @Getter
  private final String vcsBranch;

  /**
   * Revision identifier such as a revision hash or version number
   */
  @Getter
  private final String revision;

  /**
   * MD5 for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter
  @JsonDeserialize(using = ChecksumDeserializer.class)
  @JsonSerialize(using = ChecksumSerializer.class)
  private final byte[] checksumMd5;

  /**
   * SHA1 for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter
  @JsonDeserialize(using = ChecksumDeserializer.class)
  @JsonSerialize(using = ChecksumSerializer.class)
  private final byte[] checksumSha1;

  @Singular
  private final Map<String, Object> extValues;

  @JsonAnyGetter
  Map<String, Object> getExtValues() {
    return this.extValues;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public final static class Builder {
    @JsonAnySetter
    protected /*TOTEST*/ void unknownField(String key, Object value) {
      extValue(key, value);
    }

    public static About readFromFile(File src) throws IOException {
      final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
      final SimpleModule deserializersModule = new SimpleModule();
      deserializersModule.addDeserializer(Path.class, new RelativePathDeserializer(src));
      deserializersModule.addDeserializer(AnyLicenseInfo.class, new SpdxAnyLicenseInfoDeserializer());
      objectMapper.registerModule(deserializersModule);
      return objectMapper.readValue(src, About.class);
    }
  }

  public static About readFromFile(File src) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    final SimpleModule serializersModule = new SimpleModule();
    serializersModule.addSerializer(Path.class, new RelativePathSerializer(src));
    serializersModule.addSerializer(AnyLicenseInfo.class, new ToStringSerializer());
    objectMapper.registerModule(serializersModule);
    return objectMapper.readValue(src, About.class);
  }
}
