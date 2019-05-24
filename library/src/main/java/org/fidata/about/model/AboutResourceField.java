package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.fidata.utils.PathAbsolutizer;

public class AboutResourceField extends PathField {
  public AboutResourceField(
    @JacksonInject(value = PATH_ABSOLUTIZER, useInput = OptBoolean.FALSE) PathAbsolutizer pathAbsolutizer,
    String stringValue
  ) {
    super(pathAbsolutizer, stringValue);
  }
}
