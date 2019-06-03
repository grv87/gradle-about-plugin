package org.fidata.about.maven;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.maven.scm.manager.ScmManager;
import org.fidata.about.extended.maven.CiManagement;
import org.fidata.about.extended.maven.Contributor;
import org.fidata.about.extended.maven.Developer;
import org.fidata.about.extended.maven.IssueManagement;
import org.fidata.about.extended.maven.LicenseExtended;
import org.fidata.about.extended.maven.MailingList;
import org.fidata.about.extended.maven.Organization;
import org.fidata.about.extended.ExtendedAbout;
import org.fidata.about.model.License;
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

  @Getter(onMethod_ = {@JsonProperty("maven_organization")})
  private final Organization organization;

  @Getter(onMethod_ = {@JsonProperty("maven_developers")})
  @Builder.Default
  private final List<Developer> developers = ImmutableList.of();

  @Getter(onMethod_ = {@JsonProperty("maven_contributors")})
  @Builder.Default
  private final List<Contributor> contributors = ImmutableList.of();

  @Getter(onMethod_ = {@JsonProperty("maven_issue_management")})
  private final IssueManagement issueManagement;

  @Getter(onMethod_ = {@JsonProperty("maven_ci_management")})
  private final CiManagement ciManagement;

  @Getter(onMethod_ = {@JsonProperty("maven_mailing_lists")})
  @Singular
  private final List<? extends MailingList> mailingLists;

  @JacksonInject
  @NonNull
  private final ScmManager scmManager; // TODO

  private UrlField constructVcsConnectionUrl()  {
    final String vcsConnectionUrl = "scm:" + getVcsTool() + ":" + getVcsRepository();
    final List<String> validationErrors = scmManager.validateScmRepository(vcsConnectionUrl);
    if (!validationErrors.isEmpty()) {
      throw new IllegalStateException(String.format("Invalid vcs connection URL: %s.\n%s", vcsConnectionUrl, validationErrors.toString()));
    }
    try {
      return new UrlField(new URL(vcsConnectionUrl));
    } catch (MalformedURLException e) {
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
    @JsonDeserialize(contentAs = LicenseExtended.class)
    @Override
    public B licenses(/*@NonNull*/ final Set<? extends License/*Extended*/> licenses) {
      return super.licenses(licenses);
    }
  }


  protected static final class MavenAboutBuilderImpl extends MavenAboutBuilder<MavenAbout, MavenAboutBuilderImpl> {}

  public static MavenAbout readFromFile(File src) throws IOException {
    return readFromFile(src, MavenAbout.class);
  }
}
