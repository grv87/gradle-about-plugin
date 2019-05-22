package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class BooleanField extends Field<Boolean> {
  public static final BooleanField FALSE = new BooleanField(false);
  public static final BooleanField TRUE = new BooleanField(true);

  private BooleanField(Boolean booleanValue) {
    super(booleanValue);
  }

  @JsonCreator
  public static BooleanField of(Boolean booleanValue) { // TOTEST
    if (booleanValue == Boolean.TRUE) {
      return TRUE;
    } else if (booleanValue == Boolean.FALSE) {
      return FALSE;
    }
    return null;
  }
}
