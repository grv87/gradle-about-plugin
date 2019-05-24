package org.fidata.about.gradle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.AbstractFieldSet;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = Organization.OrganizationBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Organization extends AbstractFieldSet {
  @Getter
  private final StringField name;

  @Getter
  private final UrlField url;

  protected static final class OrganizationBuilderImpl extends OrganizationBuilder<Organization, OrganizationBuilderImpl> {}
}
