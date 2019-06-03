package org.fidata.about.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.StringField;

@JsonDeserialize(builder = Developer.DeveloperBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Developer extends Contributor {
  @Getter
  private final StringField id;

  protected static final class DeveloperBuilderImpl extends DeveloperBuilder<Developer, DeveloperBuilderImpl> {}
}
