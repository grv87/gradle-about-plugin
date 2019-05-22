package org.fidata.about.model;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import static org.fidata.about.model.FileTextField.PATH_ABSOLUTIZER;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;
import com.github.jonpeterson.jackson.module.versioning.VersioningModule;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import org.fidata.jackson.RelativePathDeserializer;
import org.fidata.jackson.SpdxAnyLicenseInfoDeserializer;
import org.fidata.jackson.VersionParser;
import org.fidata.utils.PathAbsolutizer;
import org.spdx.rdfparser.license.AnyLicenseInfo;

@JsonDeserialize(builder = About.Builder.class)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "Builder")
@JsonVersionedModel(propertyName = "specVersion", currentVersion = "3.1.2", toCurrentConverterClass = About.ToCurrentAboutConverter.class)
@EqualsAndHashCode
public class About {
  private static final String FILE_FIELD_SUFFIX = "_file";
  private static final String URL_FIELD_SUFFIX = "_url";
  private static final String CHECKSUM_FIELD_PREFIX = "checksum_";

  private static final String CURRENT_SPEC_VERSION = "3.1.2";

  @Getter
  @NonNull
  private final FileTextField aboutResource;

  @Getter
  @NonNull
  private final StringField name;

  @Getter
  @lombok.Builder.Default
  private final StringField specVersion = new StringField(CURRENT_SPEC_VERSION); // TOTEST

  /**
   * Component description, as a short text
   */
  public final StringField getDescription() {
    return getString("description");
  }

  /**
   * A direct URL to download the original file or archive documented by this ABOUT file
   */
  public final UrlField getDownloadUrl() {
    return getUrl("download");
  }

  /**
   * URL to the homepage for this component
   */
  public final UrlField getHomepageUrl() {
    return getUrl("homepage");
  }

  /**
   * Changelog file for the component
   */
  public final FileTextField getChangelogFile() {
    return getFile("changelog");
  }

  /**
   * Notes and comments about the component
   */
  public final StringField getNotes() {
    return getString("notes");
  }

  /**
   * The name of the primary organization or person(s) that owns or provides the component
   */
  public final StringField getOwner() {
    return getString("owner");
  }

  /**
   * URL to the homepage for the owner
   */
  public final UrlField getOwnerUrl() {
    return getUrl("owner");
  }

  /**
   * Contact information (such as an email address or physical address) for the component owner
   */
  public final StringField getContact() {
    return getString("contact");
  }

  /**
   * Name of the organization(s) or person(s) that authored the component
   */
  public final StringField getAuthor() {
    return getString("author");
  }

  /**
   * Author file for the component
   */
  public final FileTextField getAuthorFile() {
    return getFile("author");
  }

  /**
   * Copyright statement for the component
   */
  public final StringField getCopyright() {
    return getString("copyright");
  };

  /**
   * Legal notice or credits for the component
   */
  public final FileTextField getNoticeFile() {
    return getFile("notice");
  }

  /**
   * URL to a legal notice for the component
   */
  public final UrlField getNoticeUrl() {
    return getUrl("notice");
  }

  @Getter
  private final List<License> licenses;

  /**
   * The license expression that apply to the component
   *
   * You can separate each identifier using " or " and " and " to document the relationship
   * between multiple license identifiers, such as a choice among multiple licenses
   */
  @Getter
  private final AnyLicenseInfo licenseExpression;

  /**
   * Set this flag to yes if the component license requires source code redistribution
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final BooleanField redistribute = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component license requires publishing an attribution or credit notice
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final BooleanField attribute = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component license requires tracking changes made to a the component
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final BooleanField trackChanges = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component has been modified
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final BooleanField modified = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component is used internal only
   *
   * Defaults to no when absent
   */
  @Getter
  @lombok.Builder.Default
  private final BooleanField internalUseOnly = BooleanField.FALSE;

  /**
   * VCS tool such as git, svn, cvs, etc.
   */
  public final StringField getVcsTool() {
    return getString("vcsTool");
  }

  /**
   * Typically a URL or some other identifier used by a VCS tool
   * to point to a repository such as an SVN or Git repository URL
   */
  public final StringField getVcsRepository() {
    return getString("vcsRepository");
  }

  /**
   * Path used by a particular VCS tool to point to a file, directory or module inside a repository
   */
  public final StringField getVcsPath() {
    return getString("vcsPath");
  }

  /**
   * Tag name or path used by a particular VCS tool
   */
  public final StringField getVcsTag() {
    return getString("vcsTag");
  }

  /**
   * Branch name or path used by a particular VCS tool
   */
  public final StringField getVcsBranch() {
    return getString("vcsBranch");
  }

  /**
   * Revision identifier such as a revision hash or version number
   */
  public final StringField getRevision() {
    return getString("revision");
  }

  /**
   * Checksums for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter // TODO: check that it is immutable
  @Singular
  private final Map<String, ChecksumField> checksums;

  @Singular
  private final Map<String, ? extends Field> fields;

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String key) {
    return (FileTextField)fields.get(Locale.ROOT + FILE_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String extensionPrefix, String key) {
    return (FileTextField)fields.get(extensionPrefix + capitalize(key) + FILE_FIELD_SUFFIX); // TODO
  }

  /**
   * Extension fields referencing URLs
   */
  public final UrlField getUrl(String key) {
    return (UrlField)fields.get(key + URL_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing URLs
   */
  public final UrlField getUrl(String extensionPrefix, String key) {
    return (UrlField)fields.get(extensionPrefix + capitalize(key) + URL_FIELD_SUFFIX);
  }

  /**
   * Other (than file and URL) extension fields
   */
  public final StringField getString(String key) {
    return (StringField)fields.get(key);
  }

  /**
   * Other (than file and URL) extension fields
   */
  public final StringField getString(String extensionPrefix, String key) {
    return (StringField)fields.get(extensionPrefix + capitalize(key));
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public final static class Builder {
    @Setter
    @JacksonInject(PATH_ABSOLUTIZER)
    private PathAbsolutizer pathAbsolutizer;

    @JsonAnySetter
    protected /*TOTEST*/ void unknownField(String key, String value) throws MalformedURLException {
      // TODO: more efficient way ?
      if (endsWithIgnoreCase(key, FILE_FIELD_SUFFIX)) {
        field(key, new FileTextField(pathAbsolutizer, value));
      } else if (endsWithIgnoreCase(key, URL_FIELD_SUFFIX)) {
        field(key, new UrlField(value));
      } else if (startsWithIgnoreCase(key, CHECKSUM_FIELD_PREFIX)) {
        checksum(key.substring(CHECKSUM_FIELD_PREFIX.length()), new ChecksumField(value));
      } else {
        field(key, new StringField(value));
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

  protected static class ToCurrentAboutConverter implements VersionedModelConverter {
    private static final Version VERSION_2_0 = new Version(2, 0, 0, "", null, null);
    // TODO: private static final Version VERSION_3_0 = new Version(3, 0, 0, "", null, null);
    private static final Version VERSION_3_1 = new Version(3, 1, 0, "", null, null);

    @Override
    public ObjectNode convert(ObjectNode modelData, String modelVersion, String targetModelVersion, JsonNodeFactory nodeFactory) {
      Version version = VersionParser.parse(modelVersion);
      Version targetVersion = VersionParser.parse(targetModelVersion);

      if (targetVersion.compareTo(VERSION_3_1) >= 0 && version.compareTo(VERSION_3_1) <= 0) {
        // TODO: test that 1) case is ignored here 2) field names should be before PropertyNamingStrategy transformation
        modelData.put("homepage_url", modelData.get("home_url").textValue());
        // Making all paths starting with / relative
        Iterator<String> fieldNamesIterator = modelData.fieldNames();
        String fieldName;
        while ((fieldName = fieldNamesIterator.next()) != null) {
          if (endsWithIgnoreCase(fieldName, FILE_FIELD_SUFFIX)) {
            String fieldValue = modelData.get(fieldName).textValue();
            if (fieldValue.startsWith("/")) {
              modelData.set(fieldName, new TextNode("." + fieldValue));
            }
          }
        }
      }

      if (targetVersion.compareTo(VERSION_2_0) >= 0 && version.compareTo(VERSION_2_0) <= 0) {
        modelData.put("license_file", modelData.get("license_text_file").textValue());
        modelData.put("license_expression", modelData.get("license_spdx").textValue());
      }

      return modelData;
    }
  }
}
