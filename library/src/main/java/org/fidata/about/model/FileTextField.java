package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.nio.file.Path;
import org.fidata.utils.PathAbsolutizer;

public class FileTextField extends PathField {
  public FileTextField(
    @JacksonInject(value = PATH_ABSOLUTIZER, useInput = OptBoolean.FALSE) PathAbsolutizer pathAbsolutizer,
    String stringValue
  ) {
    super(pathAbsolutizer, stringValue);
  }
}
