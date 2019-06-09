// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.License;
import org.fidata.about.model.StringField;

@JsonDeserialize(builder = LicenseExtended.LicenseExtendedBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LicenseExtended extends License {
  public StringField getComments() {
    return getString("maven", "comments");
  }

  public StringField getDistribution() {
    return getString("maven", "distribution");
  }

  protected static final class LicenseExtendedBuilderImpl extends LicenseExtendedBuilder<LicenseExtended, LicenseExtendedBuilderImpl> {}
}
