package org.fidata.about.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@JsonDeserialize(builder = License.LicenseBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class License extends AbstractFieldSet {
  /**
   * The license key for the component
   */
  @Getter
  private final StringField key;

  /**
   * The license short name for the license
   */
  @Getter
  private final StringField name;

  /**
   * License file that applies to the component
   *
   * For example, the name of a license file such as LICENSE or COPYING file extracted from a downloaded archive
   */
  @Getter
  private final FileTextField file;

  /**
   * URL to the license text for the component
   */
  @Getter
  private final UrlField url;

  protected static final class LicenseBuilderImpl extends LicenseBuilder<License, LicenseBuilderImpl> {}
}
