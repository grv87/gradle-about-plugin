package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

@ToString(callSuper = true)
public final class StringField extends Field<String> {
  public static final StringField NULL = new StringField(null);
  public static final StringField EMPTY = new StringField("");

  @JsonCreator
  StringField(String stringValue) {
    super(stringValue);
  }
}
