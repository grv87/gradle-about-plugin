package org.fidata.about.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.AbstractFieldSet;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = IssueManagement.IssueManagementBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class IssueManagement extends AbstractFieldSet {
  @Getter
  private final StringField system;

  @Getter
  private final UrlField url;

  protected static final class IssueManagementBuilderImpl extends IssueManagementBuilder<IssueManagement, IssueManagementBuilderImpl> {}
}
