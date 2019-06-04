package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.ToString;
import org.fidata.utils.PathAbsolutizer;

@ToString(callSuper = true)
public class AboutResourceField extends PathField {
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public AboutResourceField(
    @JacksonInject(value = PATH_ABSOLUTIZER, useInput = OptBoolean.FALSE) PathAbsolutizer pathAbsolutizer,
    String stringValue
  ) {
    super(pathAbsolutizer, stringValue);
  }
}
