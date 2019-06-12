// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
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
  @Singular
  private final Set<StringField> roles;

  @Getter
  @Default
  private final StringField timezone = StringField.NULL;

  @Getter
  @Singular
  private final Map<String, StringField> properties;

  public static abstract class ContributorBuilder<C extends Contributor, B extends ContributorBuilder<C, B>> extends AbstractFieldSet.AbstractFieldSetBuilder<C, B> implements ContributorBuilderMeta {
  }

  private interface ContributorBuilderMeta {
    @JsonIgnore
    ContributorBuilder role(StringField role);
  }

  protected static final class ContributorBuilderImpl extends ContributorBuilder<Contributor, ContributorBuilderImpl> {}
}
