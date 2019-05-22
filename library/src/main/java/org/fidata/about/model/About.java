package org.fidata.about.model;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;
import com.github.jonpeterson.jackson.module.versioning.VersioningModule;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import org.fidata.jackson.RelativePathDeserializer;
import org.fidata.jackson.SpdxAnyLicenseInfoDeserializer;
import org.fidata.jackson.VersionParser;
import org.fidata.utils.PathAbsolutizer;
import org.spdx.rdfparser.license.AnyLicenseInfo;

@JsonDeserialize(builder = About.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
  private final FileTextField aboutResource;

  @Getter
  private final StringField name;

  @Getter
  @lombok.Builder.Default
  private final StringField specVersion = new StringField(CURRENT_SPEC_VERSION); // TOTEST

  /**
   * Component description, as a short text
   */
  @JsonIgnore
  public final StringField getDescription() {
    return getString("description");
  }

  /**
   * A direct URL to download the original file or archive documented by this ABOUT file
   */
  @JsonIgnore
  public final UrlField getDownloadUrl() {
    return getUrl("download");
  }

  /**
   * URL to the homepage for this component
   */
  @JsonIgnore
  public final UrlField getHomepageUrl() {
    return getUrl("homepage");
  }

  /**
   * Changelog file for the component
   */
  @JsonIgnore
  public final FileTextField getChangelogFile() {
    return getFile("changelog");
  }

  /**
   * Notes and comments about the component
   */
  @JsonIgnore
  public final StringField getNotes() {
    return getString("notes");
  }

  /**
   * The name of the primary organization or person(s) that owns or provides the component
   */
  @JsonIgnore
  public final StringField getOwner() {
    return getString("owner");
  }

  /**
   * URL to the homepage for the owner
   */
  @JsonIgnore
  public final UrlField getOwnerUrl() {
    return getUrl("owner");
  }

  /**
   * Contact information (such as an email address or physical address) for the component owner
   */
  @JsonIgnore
  public final StringField getContact() {
    return getString("contact");
  }

  /**
   * Name of the organization(s) or person(s) that authored the component
   */
  @JsonIgnore
  public final StringField getAuthor() {
    return getString("author");
  }

  /**
   * Author file for the component
   */
  @JsonIgnore
  public final FileTextField getAuthorFile() {
    return getFile("author");
  }

  /**
   * Copyright statement for the component
   */
  @JsonIgnore
  public final StringField getCopyright() {
    return getString("copyright");
  };

  /**
   * Legal notice or credits for the component
   */
  @JsonIgnore
  public final FileTextField getNoticeFile() {
    return getFile("notice");
  }

  /**
   * URL to a legal notice for the component
   */
  @JsonIgnore
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
  @Getter
  private final StringField vcsTool;

  /**
   * Typically a URL or some other identifier used by a VCS tool
   * to point to a repository such as an SVN or Git repository URL
   */
  @Getter
  private final StringField vcsRepository;

  /**
   * Path used by a particular VCS tool to point to a file, directory or module inside a repository
   */
  @Getter
  private final StringField vcsPath;

  /**
   * Tag name or path used by a particular VCS tool
   */
  @Getter
  private final StringField vcsTag;

  /**
   * Branch name or path used by a particular VCS tool
   */
  @Getter
  private final StringField vcsBranch;

  /**
   * Revision identifier such as a revision hash or version number
   */
  @Getter
  private final StringField revision;

  /**
   * Checksums for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter // TODO: check that it is immutable
  @JsonIgnore
  @Singular
  private final Map<String, ChecksumField> checksums;

  @Singular
  private final Map<String, ? extends Field> fields;

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String key) {
    return (FileTextField)fields.get(key + FILE_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing URLs
   */
  @JsonIgnore
  public final UrlField getUrl(String key) {
    return (UrlField)fields.get(key + URL_FIELD_SUFFIX);
  }

  /**
   * Other (than file and URL) extension fields
   */
  @JsonIgnore
  public final StringField getString(String key) {
    return (StringField)fields.get(key);
  }

  @JsonPOJOBuilder(withPrefix = "")
  public final static class Builder {
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
    injectableValues.addValue(FileTextField.PATH_ABSOLUTIZER, pathAbsolutizer);
    objectMapper.setInjectableValues(injectableValues);

    objectMapper.registerModule(VERSIONING_MODULE);

    return objectMapper.readValue(src, About.class);
  }

  protected static class AboutDeserializer extends StdDeserializer<About> {
    public AboutDeserializer() {
      super(About.class);
    }

    @Override
    public About deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      Builder builder = new Builder();
      String fieldName;
      while ((fieldName = p.nextFieldName()) != null) {
        if (equalsIgnoreCase(fieldName, "about_resource")) {
          builder.aboutResource(p.readValueAs(FileTextField.class));
        } else if (equalsIgnoreCase(fieldName, "name")) {
          builder.name(p.readValueAs(StringField.class));
        } else if (startsWithIgnoreCase(fieldName, CHECKSUM_FIELD_PREFIX)) {
          builder.checksum(fieldName.substring(CHECKSUM_FIELD_PREFIX.length()), p.readValueAs(ChecksumField.class));
        } else {
          Field fieldValue;
          if (endsWithIgnoreCase(fieldName, FILE_FIELD_SUFFIX)) {
            fieldName = fieldName.substring(0, fieldName.length() - FILE_FIELD_SUFFIX.length());
            fieldValue = p.readValueAs(FileTextField.class);
          } else if (endsWithIgnoreCase(fieldName, URL_FIELD_SUFFIX)) {
            fieldName = fieldName.substring(0, fieldName.length() - URL_FIELD_SUFFIX.length());
            fieldValue = p.readValueAs(UrlField.class);
          } else {
            fieldValue = p.readValueAs(StringField.class);
          }
          builder.field(fieldName, fieldValue);
        }
      }
      return builder.build();
    }
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
