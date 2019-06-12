// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model;

import static lombok.Builder.Default;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@JsonDeserialize(builder = License.LicenseBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class License extends AbstractFieldSet {
  /**
   * The license key for the component
   */
  @Getter
  @Default
  private final StringField key = StringField.NULL;

  /**
   * The license short name for the license
   */
  @Getter
  @Default
  private final StringField name = StringField.NULL;

  /**
   * License file that applies to the component
   *
   * For example, the name of a license file such as LICENSE or COPYING file extracted from a downloaded archive
   */
  @Getter
  // @Default
  @Singular
  private final List<FileTextField> files;

  /**
   * URL to the license text for the component
   */
  @Getter
  @Default
  private final UrlField url = UrlField.NULL;

  protected static final class LicenseBuilderImpl extends LicenseBuilder<License, LicenseBuilderImpl> {}
}