package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

@ToString(callSuper = true)
public final class StringField extends Field<String> {
  @JsonCreator
  StringField(String stringValue) {
    super(stringValue);
  }
}
