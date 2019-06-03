package org.fidata.about.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
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
  @Default
  private final StringField name = StringField.NULL;

  @Getter
  @Default
  private final StringField email = StringField.NULL;

  @Getter
  @Default
  private final StringField organization = StringField.NULL;

  @Getter
  @Default
  private final UrlField organizationUrl = UrlField.NULL;

  @Getter
  @Default
  private final Set<StringField> roles = ImmutableSet.of();

  @Getter
  @Default
  private final StringField timezone = StringField.NULL;

  @Getter
  @Default
  private final Map<String, StringField> properties = ImmutableMap.of();

  protected static final class ContributorBuilderImpl extends ContributorBuilder<Contributor, ContributorBuilderImpl> {}
}
