package org.fidata.about.gradle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.About;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = AboutExtended.AboutExtendedBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AboutExtended extends About {
  /**
   * Versioning schema used for version field
   *
   * For example, `SemVer v2.0.0` or `CalVer`.
   * If the version was made from the date the component was provisioned,
   * value `ProvisionDate` should be used.
   */
  public StringField getVersioningSchema() {
    return getString("gradle", "versioning_schema");
  }

  @Getter
  @Singular
  private final List<String> keywords;

  public StringField getInceptionYear() {
    return getString("maven", "inception_year");
  }

  @Getter
  private final Organization organization;

  @Getter
  private final IssueManagement issueManagement;

  @Getter
  private final CiManagement ciManagement;

  @Getter
  @Singular
  private final List<MailingList> mailingLists;

  public UrlField getVcsConnectionUrl() {
    new UrlField(new URL())
    // TODO
  }

  public UrlField getVcsDeveloperConnectionUrl() {
    return getUrl("maven", "vcs_developer_connection_url");
  }

  public UrlField getVcsUrl() {
    return getUrl("maven", "vcs_url");
  }

  protected static final class AboutExtendedBuilderImpl extends AboutExtendedBuilder<AboutExtended, AboutExtendedBuilderImpl> {}

  public static AboutExtended readFromFile(File src) throws IOException {
    return readFromFile(src, AboutExtended.class);
  }
}
