// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

@ToString(callSuper = true)
public final class StringField extends Field<String> {
  public static final StringField NULL = new StringField(null);
  public static final StringField EMPTY = new StringField("");

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  StringField(String stringValue) {
    super(stringValue);
  }
}
