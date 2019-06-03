package org.fidata.about.maven;

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
  private final StringField system;

  @Getter
  private final UrlField url;

  protected static final class CiManagementBuilderImpl extends CiManagementBuilder<CiManagement, CiManagementBuilderImpl> {}
}
