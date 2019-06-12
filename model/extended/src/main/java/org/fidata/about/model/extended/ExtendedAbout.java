// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.extended;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.About;
import org.fidata.about.model.StringField;

@JsonDeserialize(builder = ExtendedAbout.ExtendedAboutBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ExtendedAbout extends About {
  /**
   * Versioning schema used for version field
   *
   * For example, `SemVer v2.0.0` or `CalVer`.
   * If the version was made from the date the component was provisioned,
   * value `ProvisionDate` should be used.
   */
  public StringField getVersioningSchema() {
    return getString("extended", "versioning_schema");
  }

  @JsonProperty("extended_keywords")
  @Getter
  @Singular
  private final Set<StringField> keywords;

  protected static final class ExtendedAboutBuilderImpl extends ExtendedAboutBuilder<ExtendedAbout, ExtendedAboutBuilderImpl> {}

  public static ExtendedAbout readFromFile(File src) throws IOException {
    return readFromFile(src, ExtendedAbout.class);
  }
}
