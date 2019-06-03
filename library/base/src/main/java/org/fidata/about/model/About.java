package org.fidata.about.model;

import static lombok.Builder.Default;
import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import static org.fidata.about.model.FileTextField.PATH_ABSOLUTIZER;
import static org.fidata.spdx.SpdxUtils.walkLicenseInfo;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;
import com.github.jonpeterson.jackson.module.versioning.VersioningModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.jena.util.FileUtils;
import org.fidata.jackson.VersionParser;
import org.fidata.spdx.AnyLicenseInfoWalker;
import org.fidata.utils.PathAbsolutizer;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;
import org.spdx.rdfparser.license.LicenseException;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoneLicense;

@JsonDeserialize(builder = About.AboutBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class About extends AbstractFieldSet {
  private static final String CHECKSUM_FIELD_PREFIX = "checksum_";

  private static final String CURRENT_SPEC_VERSION = "3.1.2";

  /**
   * The resource this file referencing to
   */
  @Getter
  @NonNull
  private final AboutResourceField aboutResource;

  /**
   * Component name
   */
  @Getter
  @NonNull
  private final StringField name;

  /**
   * Component or package version
   * <p>
   * A component or package usually has a version, such as a revision number or hash from a version control system
   * (for a snapshot checked out from VCS such as Subversion or Git).
   * If not available, the version should be the date the component was provisioned,
   * in an ISO date format such as 'YYYY-MM-DD'.
   */
  public final StringField getVersion() {
    return getString("version");
  }

  /**
   * The version of the ABOUT file format specification used for this file
   * <p>
   * This is provided as a hint to readers and tools in order to support future versions of this specification
   */
  @Getter
  @Default
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
  }

  ;

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
  @Default
  private final Set<? extends License> licenses = ImmutableSet.of();

  /**
   * The license expression that apply to the component
   * <p>
   * You can separate each identifier using " or " and " and " to document the relationship
   * between multiple license identifiers, such as a choice among multiple licenses
   */
  @Getter
  @Default
  private final LicenseExpressionField licenseExpression = new LicenseExpressionField(new SpdxNoneLicense());

  /**
   * Set this flag to yes if the component license requires source code redistribution
   * <p>
   * Defaults to no when absent
   */
  @Getter
  @Default
  private final BooleanField redistribute = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component license requires publishing an attribution or credit notice
   * <p>
   * Defaults to no when absent
   */
  @Getter
  @Default
  private final BooleanField attribute = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component license requires tracking changes made to a the component
   * <p>
   * Defaults to no when absent
   */
  @Getter
  @Default
  private final BooleanField trackChanges = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component has been modified
   * <p>
   * Defaults to no when absent
   */
  @Getter
  @Default
  private final BooleanField modified = BooleanField.FALSE;

  /**
   * Set this flag to yes if the component is used internal only
   * <p>
   * Defaults to no when absent
   */
  @Getter
  @Default
  private final BooleanField internalUseOnly = BooleanField.FALSE;

  /**
   * VCS tool such as git, svn, cvs, etc.
   */
  public final StringField getVcsTool() {
    return getString("vcs_tool");
  }

  /**
   * Typically a URL or some other identifier used by a VCS tool
   * to point to a repository such as an SVN or Git repository URL
   */
  public final StringField getVcsRepository() {
    return getString("vcs_repository");
  }

  /**
   * Path used by a particular VCS tool to point to a file, directory or module inside a repository
   */
  public final StringField getVcsPath() {
    return getString("vcs_path");
  }

  /**
   * Tag name or path used by a particular VCS tool
   */
  public final StringField getVcsTag() {
    return getString("vcs_tag");
  }

  /**
   * Branch name or path used by a particular VCS tool
   */
  public final StringField getVcsBranch() {
    return getString("vcs_branch");
  }

  /**
   * Revision identifier such as a revision hash or version number
   */
  public final StringField getVcsRevision() {
    return getString("vcs_revision");
  }

  /**
   * Checksums for the file documented by this ABOUT file in the "about_resource" field
   */
  @Getter // TODO: check that it is immutable
  @Singular
  private final Map<String, ? extends ChecksumField> checksums;

  @JsonVersionedModel(propertyName = "specVersion", currentVersion = CURRENT_SPEC_VERSION, defaultDeserializeToVersion = CURRENT_SPEC_VERSION, toCurrentConverterClass = About.ToCurrentAboutConverter.class)
  public static abstract class AboutBuilder<C extends About, B extends AboutBuilder<C, B>> extends AbstractFieldSetBuilder<C, B> {
    private License.LicenseBuilder<? extends License, ? extends License.LicenseBuilder> licenseBuilder;

    private License.LicenseBuilder<? extends License, ? extends License.LicenseBuilder> getLicenseBuilder() {
      if (licenseBuilder == null) {
        licenseBuilder = License.builder();
      }
      return licenseBuilder;
    }

    @Override
    protected boolean parseUnknownFileTextField(String name, FileTextField value) {
      if ("license_file".equals(name)) {
        getLicenseBuilder().file(value);
        return true;
      }
      return false;
    }

    protected boolean parseUnknownUrlField(String name, UrlField value) {
      if ("license_url".equals(name)) {
        getLicenseBuilder().url(value);
        return true;
      }
      return false;
    }


    @Override
    protected boolean parseUnknownField(String name, Object value) {
      if (startsWithIgnoreCase(name, CHECKSUM_FIELD_PREFIX)) {
        checksum(name.substring(CHECKSUM_FIELD_PREFIX.length()), new ChecksumField((String)value));
        return true;
      } else if ("license_key".equals(name)) {
        licenseBuilder.key(new StringField((String)value));
        return true;
      } else if ("license_name".equals(name)) {
        licenseBuilder.name(new StringField((String)value));
        return true;
      }
      return false;
    }

    @Override
    protected void doValidate() {
      if (licenseBuilder != null) {
        // TODO: either one or another
        // licenses.add(licenseBuilder.validate());
      }

      if (licenseExpression$set) {
        AnyLicenseInfo licenseInfo = licenseExpression.getOriginalValue();
        if (licenses != null) {
          for (License license : licenses) {
            String licenseKey = license.getKey().getValue();
            Path licenseFile = license.getFile().getValue();
            if (licenseKey != null && licenseFile != null) {
              String licenseText;
              try {
                licenseText = FileUtils.readWholeFileAsUTF8(licenseFile.toString());
              } catch (IOException ignored) {
                // Don't use problematic license file
                // TOTHINK about it
                continue;
              }
              walkLicenseInfo(licenseInfo, new AnyLicenseInfoWalker() {
                @Override
                public void visitSimpleLicensingInfo(SimpleLicensingInfo simpleLicensingInfo) {
                  if (ExtractedLicenseInfo.class.isInstance(simpleLicensingInfo)) {
                    ExtractedLicenseInfo extractedLicenseInfo = (ExtractedLicenseInfo)simpleLicensingInfo;
                    if (licenseKey.equals(extractedLicenseInfo.getLicenseId())) {
                      extractedLicenseInfo.setExtractedText(licenseText);
                    }
                  } else if (org.spdx.rdfparser.license.License.class.isInstance(simpleLicensingInfo)) {
                    org.spdx.rdfparser.license.License license = (org.spdx.rdfparser.license.License)simpleLicensingInfo;
                    if (licenseKey.equals(license.getLicenseId())) {
                      license.setLicenseText(licenseText);
                    }
                  }
                }

                @Override
                public void visitException(LicenseException licenseException) {
                  if (licenseKey.equals(licenseException.getLicenseExceptionId())) {
                    licenseException.setLicenseExceptionText(licenseText);
                  }
                }
              });
            }
          }
        }

        final List<String> validationErrors = licenseInfo.verify();
        if (!validationErrors.isEmpty()) {
          // TODO: Groovy had nicer formatting
          throw new IllegalArgumentException(String.format("License \'%s\' is not valid. Validation errors: %s", licenseInfo.toString(), validationErrors.toString()));
        }
      }
    }
  }

  protected static final class AboutBuilderImpl extends AboutBuilder<About, AboutBuilderImpl> {
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());
  static {
    OBJECT_MAPPER.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    OBJECT_MAPPER.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES); // TODO
    /*
     * This doesn't work due to bug in Jackson.
     * See https://github.com/FasterXML/jackson-databind/issues/2024
     * <grv87 2019-05-28>
     */
    OBJECT_MAPPER.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.FAIL, Nulls.FAIL));

    OBJECT_MAPPER.registerModule(new GuavaModule());

    final SimpleModule deserializersModule = new SimpleModule();
    deserializersModule.addAbstractTypeMapping(List.class, ImmutableList.class);
    deserializersModule.addAbstractTypeMapping(Set.class, ImmutableSet.class);
    deserializersModule.addAbstractTypeMapping(Map.class, ImmutableMap.class);
    OBJECT_MAPPER.registerModule(deserializersModule);

    OBJECT_MAPPER.registerModule(new VersioningModule());
  }

  private static <U, V> void addAbstractTypeMapping(SimpleModule simpleModule, Class<U> superClass, Class<V> subClass) {
    simpleModule.addAbstractTypeMapping(superClass, subClass.asSubclass(superClass));
  }

  protected static <T extends About> T readFromFile(File src, Class<T> tClass, Map<Class<?>, Class<?>> abstractTypeMappings) throws IOException {
    final ObjectMapper objectMapper = OBJECT_MAPPER.copy();

    final InjectableValues.Std injectableValues = new InjectableValues.Std();
    injectableValues.addValue(PATH_ABSOLUTIZER, new PathAbsolutizer(src.getParentFile()));
    objectMapper.setInjectableValues(injectableValues);

    if (abstractTypeMappings.size() > 0) {
      final SimpleModule deserializersModule = new SimpleModule();
      for (Class<?> abstractType : abstractTypeMappings.keySet()) {
        addAbstractTypeMapping(deserializersModule, abstractType, abstractTypeMappings.get(abstractType));
      }
      objectMapper.registerModule(deserializersModule);
    }

    return objectMapper.readValue(src, tClass);
  }

  public static About readFromFile(File src) throws IOException {
    return readFromFile(src, About.class, ImmutableMap.of());
  }

  public static class ToCurrentAboutConverter implements VersionedModelConverter {
    private static final Version VERSION_2_0 = new Version(2, 0, 0, "", null, null);
    // TODO: private static final Version VERSION_3_0 = new Version(3, 0, 0, "", null, null);
    private static final Version VERSION_3_1 = new Version(3, 1, 0, "", null, null);

    private void renameField(ObjectNode modelData, String oldName, String newName) {
      // TODO: test that case is ignored here
      JsonNode value = modelData.get(oldName);
      if (value != null) {
        modelData.remove(oldName);
        modelData.set(newName, value);
      }
    }

    @Override
    public ObjectNode convert(ObjectNode modelData, String modelVersion, String targetModelVersion, JsonNodeFactory nodeFactory) {
      Version version = VersionParser.parse(modelVersion);
      Version targetVersion = VersionParser.parse(targetModelVersion);

      if (targetVersion.compareTo(VERSION_3_1) >= 0 && version.compareTo(VERSION_3_1) <= 0) {
        renameField(modelData, "home_url", "homepage_url");

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
        renameField(modelData, "license_text_file", "license_file");
        renameField(modelData, "license_spdx", "license_expression");
      }

      return modelData;
    }
  }
}
