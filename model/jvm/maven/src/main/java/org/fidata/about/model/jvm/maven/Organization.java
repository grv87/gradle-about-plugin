// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.jvm.maven;

import static lombok.Builder.Default;
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
  public final StringField getName() {
    return getString("name");
  }

  @Getter
  @Default
  private final UrlField url = UrlField.NULL;

  /**
   * This field replaces `contact` field in standard .ABOUT file.
   *
   * The reason for replacement is that standard .ABOUT specification
   * doesn't differentiate persons and organizations in owners and authors fields
   */
  public final StringField getContact() {
    return getString("contact");
  }

  protected static final class OrganizationBuilderImpl extends OrganizationBuilder<Organization, OrganizationBuilderImpl> {}
}
