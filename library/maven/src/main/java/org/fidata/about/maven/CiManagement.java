package org.fidata.about.maven;

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
  @Getter
  @Default
  private final StringField system = StringField.NULL;

  @Getter
  @Default
  private final UrlField url = UrlField.NULL;

  protected static final class CiManagementBuilderImpl extends CiManagementBuilder<CiManagement, CiManagementBuilderImpl> {}
}
