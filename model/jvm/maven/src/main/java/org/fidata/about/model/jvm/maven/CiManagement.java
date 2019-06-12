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

@JsonDeserialize(builder = CiManagement.CiManagementBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CiManagement extends AbstractFieldSet {
  public final StringField getSystem() {
    return getString("system");
  }

  @Getter
  @Default
  private final UrlField url = UrlField.NULL;

  protected static final class CiManagementBuilderImpl extends CiManagementBuilder<CiManagement, CiManagementBuilderImpl> {}
}
