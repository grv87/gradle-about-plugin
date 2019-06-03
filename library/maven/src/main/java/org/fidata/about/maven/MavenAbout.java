package org.fidata.about.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.maven.scm.manager.ScmManager;
import org.fidata.about.extended.ExtendedAbout;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = MavenAbout.MavenAboutBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MavenAbout extends ExtendedAbout {
  public StringField getInceptionYear() {
    return getString("maven", "inception_year");
  }

  @SuppressWarnings("unchecked")
  @Override
  public Set<? extends LicenseExtended> getLicenses() {
    return (Set<? extends LicenseExtended>)super.getLicenses();
  }

  @Getter
  @JsonProperty("maven_organization")
  private final Organization organization;

  @Getter
  @JsonProperty("maven_developers")
  @Default
  private final Set<Developer> developers = ImmutableSet.of();

  @Getter
  @JsonProperty("maven_contributors")
  @Default
  private final Set<Contributor> contributors = ImmutableSet.of();

  @Getter
  @JsonProperty("maven_issue_management")
  private final IssueManagement issueManagement;

  @Getter
  @JsonProperty("maven_ci_management")
  private final CiManagement ciManagement;

  @Getter
  @JsonProperty("maven_mailing_lists")
  // @Default
  @Singular
  @Builder.ObtainVia
  private final Set<? extends MailingList> mailingLists /*= ImmutableSet.of()*/;

  @JacksonInject
  // @NonNull TODO
  @EqualsAndHashCode.Exclude
  private final ScmManager scmManager; // TODO

  private UrlField constructVcsConnectionUrl()  {
    final String vcsConnectionUrl = "scm:" + getVcsTool() + ":" + getVcsRepository();
    final List<String> validationErrors = scmManager.validateScmRepository(vcsConnectionUrl);
    if (!validationErrors.isEmpty()) {
      throw new IllegalStateException(String.format("Invalid vcs connection URL: %s.\n%s", vcsConnectionUrl, validationErrors.toString()));
    }
    try {
      return new UrlField(new URI(vcsConnectionUrl));
    } catch (URISyntaxException e) {
      throw new IllegalStateException(String.format("Invalid vcs connection URL: %s", vcsConnectionUrl), e);
    }
  }

  @Getter(lazy = true)
  private final UrlField vcsConnectionUrl = constructVcsConnectionUrl();

  public UrlField getVcsDeveloperConnectionUrl() {
    return getUrl("maven", "vcs_developer_connection_url");
  }

  public UrlField getVcsUrl() {
    return getUrl("maven", "vcs_url");
  }

  public static abstract class MavenAboutBuilder<C extends MavenAbout, B extends MavenAboutBuilder<C, B>> extends ExtendedAbout.ExtendedAboutBuilder<C, B> {
    // TODO: @JsonDeserialize(contentAs = LicenseExtended.class) don't work
    @JsonProperty("licenses")
    public B licensesExtended(final Set<? extends LicenseExtended> licenses) {
      return super.licenses(licenses);
    }
  }

  protected static final class MavenAboutBuilderImpl extends MavenAboutBuilder<MavenAbout, MavenAboutBuilderImpl> {}

  public static MavenAbout readFromFile(File src) throws IOException {
    return readFromFile(src, MavenAbout.class, ImmutableMap.of(/*License.class, LicenseExtended.class*/));
  }
}
