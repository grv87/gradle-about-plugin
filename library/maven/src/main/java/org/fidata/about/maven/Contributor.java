package org.fidata.about.extended.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.AbstractFieldSet;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = Contributor.ContributorBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Contributor extends AbstractFieldSet {
  @Getter
  private final StringField name;

  @Getter
  private final StringField email;

  @Getter
  private final StringField organization;

  @Getter
  private final UrlField organizationUrl;

  @Getter
  private final List<StringField> roles;

  @Getter
  private final StringField timezone;

  @Getter
  private final Map<String, StringField> properties;

  protected static final class ContributorBuilderImpl extends ContributorBuilder<Contributor, ContributorBuilderImpl> {}
}
