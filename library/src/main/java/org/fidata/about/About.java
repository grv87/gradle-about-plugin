package org.fidata.about;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import com.github.jonpeterson.jackson.module.versioning.VersioningModule;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import org.fidata.jackson.RelativePathDeserializer;
import org.fidata.jackson.RelativePathSerializer;
import org.fidata.jackson.SpdxAnyLicenseInfoDeserializer;
import org.fidata.utils.PathAbsolutizer;
import org.fidata.utils.PathRelativizer;
import org.spdx.rdfparser.license.AnyLicenseInfo;

@JsonDeserialize(builder = About.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "Builder")
@JsonVersionedModel(propertyName = "specVersion", currentVersion = "3.1.2", toCurrentConverterClass = ToCurrentAboutConverter.class)
@EqualsAndHashCode
public final class About {
  public static final String FILE_FIELD_SUFFIX = "_file";
  public static final String URL_FIELD_SUFFIX = "_url";
  private static final String CHECKSUM_FIELD_PREFIX = "checksum_";
  private static final String PATH_ABSOLUTIZER = "PATH_ABSOLUTIZER";
  /**
   * Component description, as a short text
   */
  @Getter
  private final String description;

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

  private static final BaseEncoding CHECKSUM_ENCODING = BaseEncoding.base16();

  /**
   * Checksums for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter
  @Singular
  @JsonIgnore
  private final Map<String, byte[]> checksums;

  /**
   * Extension fields referencing files
   */
  @Getter
  @Singular
  @JsonIgnore
  private final Map<String, Path> extFiles;

  /**
   * Extension fields referencing URLs
   */
  @Getter
  @Singular
  @JsonIgnore
  private final Map<String, URL> extUrls;

  /**
   * Other (than file and URL) extension fields
   */
  @Getter
  @Singular
  @JsonIgnore
  // TODO: on add test that name has no `file` or `url` suffix
  private final Map<String, String> extStrings;

  @JsonAnyGetter
  protected /* TOTEST */ Map<String, Object> getExtValues() {
    ImmutableMap.Builder<String, Object> resultBuilder = ImmutableMap.builder();
    for (Map.Entry<String, byte[]> entry : checksums.entrySet()) {
      resultBuilder.put(CHECKSUM_FIELD_PREFIX + entry.getKey(), CHECKSUM_ENCODING.encode(entry.getValue()));
    }
    resultBuilder.putAll(extStrings);
    for (Map.Entry<String, Path> entry : extFiles.entrySet()) {
      resultBuilder.put(entry.getKey() + FILE_FIELD_SUFFIX, entry.getValue());
    }
    for (Map.Entry<String, URL> entry : extUrls.entrySet()) {
      resultBuilder.put(entry.getKey() + URL_FIELD_SUFFIX, entry.getValue());
    }
    return resultBuilder.build();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public final static class Builder {
    private final PathAbsolutizer pathAbsolutizer;

    public Builder(@JacksonInject(PATH_ABSOLUTIZER) PathAbsolutizer pathAbsolutizer) {
      this.pathAbsolutizer = pathAbsolutizer;
    }

    @JsonAnySetter
    protected /*TOTEST*/ void unknownField(String key, String value) throws MalformedURLException {
      // TODO: more efficient way ?
      if (endsWithIgnoreCase(key, FILE_FIELD_SUFFIX)) {
        extFile(key.substring(0, key.length() - FILE_FIELD_SUFFIX.length()), pathAbsolutizer.absolutize(value));
      } else if (endsWithIgnoreCase(key, URL_FIELD_SUFFIX)) {
        extUrl(key.substring(0, key.length() - URL_FIELD_SUFFIX.length()), new URL(value));
      } else if (startsWithIgnoreCase(key, CHECKSUM_FIELD_PREFIX)) {
        checksum(key.substring(CHECKSUM_FIELD_PREFIX.length()), CHECKSUM_ENCODING.decode(value));
      } else {
        extString(key, value);
      }
    }
  }

  private static final VersioningModule VERSIONING_MODULE = new VersioningModule();

  public static About readFromFile(File src) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    PathAbsolutizer pathAbsolutizer = new PathAbsolutizer(src.getParentFile());

    final SimpleModule deserializersModule = new SimpleModule();
    deserializersModule.addDeserializer(Path.class, new RelativePathDeserializer(pathAbsolutizer));
    deserializersModule.addDeserializer(AnyLicenseInfo.class, new SpdxAnyLicenseInfoDeserializer());
    objectMapper.registerModule(deserializersModule);

    InjectableValues.Std injectableValues = new InjectableValues.Std();
    injectableValues.addValue(PATH_ABSOLUTIZER, pathAbsolutizer);
    objectMapper.setInjectableValues(injectableValues);

    objectMapper.registerModule(VERSIONING_MODULE);

    return objectMapper.readValue(src, About.class);
  }

  public void writeToFile(File res) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    PathRelativizer pathRelativizer = new PathRelativizer(res.getParentFile());

    final SimpleModule serializersModule = new SimpleModule();
    serializersModule.addSerializer(Path.class, new RelativePathSerializer(pathRelativizer));
    serializersModule.addSerializer(AnyLicenseInfo.class, new ToStringSerializer());
    objectMapper.registerModule(serializersModule);

    objectMapper.registerModule(VERSIONING_MODULE);

    objectMapper.writeValue(res, this);
  }
}
