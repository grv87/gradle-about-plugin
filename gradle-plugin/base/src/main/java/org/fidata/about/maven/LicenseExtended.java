package org.fidata.about.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.License;
import org.fidata.about.model.StringField;

@JsonDeserialize(builder = LicenseExtended.LicenseExtendedBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LicenseExtended extends License {
  @Getter
  private final StringField comments;

  @Getter
  private final StringField distribution;

  protected static final class LicenseExtendedBuilderImpl extends LicenseExtendedBuilder<LicenseExtended, LicenseExtendedBuilderImpl> {}
}
