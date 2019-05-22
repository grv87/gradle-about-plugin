package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class StringField extends Field<String> {
  @JsonCreator
  StringField(String stringValue) {
    super(stringValue);
  }
}
