package org.fidata.about.maven;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.apache.maven.scm.manager.ScmManager;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
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
  @Singular
  private final Set<Developer> developers;

  @Getter
  @Singular
  private final Set<Contributor> contributors;

  @Getter
  @JsonProperty("maven_issue_management")
  private final IssueManagement issueManagement;

  @Getter
  @JsonProperty("maven_ci_management")
  private final CiManagement ciManagement;

  @Getter
  @Singular
  private final Set<? extends MailingList> mailingLists;

  /* TOTHINK: maybe inject it */
  @Getter(value = AccessLevel.PRIVATE, lazy = true)
  private static final ScmManager SCM_MANAGER = initScmManager();
  @SneakyThrows({PlexusContainerException.class, ComponentLookupException.class})
  private static ScmManager initScmManager() {
    PlexusContainer plexus = new DefaultPlexusContainer();
    return (ScmManager)plexus.lookup(ScmManager.ROLE);
  }

  private UrlField constructVcsConnectionUrl()  {
    final String vcsConnectionUrl = "scm:" + getVcsTool().getValue() + ":" + getVcsRepository().getValue();
    final List<String> validationErrors = getSCM_MANAGER().validateScmRepository(vcsConnectionUrl);
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

  public static abstract class MavenAboutBuilder<C extends MavenAbout, B extends MavenAboutBuilder<C, B>> extends ExtendedAbout.ExtendedAboutBuilder<C, B> implements MavenAboutBuilderMeta {
    // TODO: @JsonDeserialize(contentAs = LicenseExtended.class) don't work
    @JsonProperty("licenses")
    public B licensesExtended(final Set<? extends LicenseExtended> licenses) {
      return super.licenses(licenses);
    }
  }

  private interface MavenAboutBuilderMeta {
    @JsonIgnore
    MavenAboutBuilder developer(Developer developer);

    @JsonProperty("maven_developers")
    MavenAboutBuilder developers(Iterable<? extends Developer> developers);

    @JsonIgnore
    MavenAboutBuilder contributor(Contributor contributor);

    @JsonProperty("maven_contributors")
    MavenAboutBuilder contributors(Iterable<? extends Contributor> contributors);

    @JsonIgnore
    MavenAboutBuilder mailingList(MailingList mailingList);

    @JsonProperty("maven_mailing_lists")
    MavenAboutBuilder mailingLists(Iterable<? extends MailingList> mailingLists);
  }

  protected static final class MavenAboutBuilderImpl extends MavenAboutBuilder<MavenAbout, MavenAboutBuilderImpl> {}

  public static MavenAbout readFromFile(File src) throws IOException {
    return readFromFile(src, MavenAbout.class);
  }
}
