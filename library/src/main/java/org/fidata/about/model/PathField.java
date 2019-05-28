package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.nio.file.Path;
import lombok.ToString;
import org.fidata.utils.PathAbsolutizer;

@ToString
public class PathField extends Field<Path> {
  static final String PATH_ABSOLUTIZER = "PATH_ABSOLUTIZER";

  public PathField(
    @JacksonInject(value = PATH_ABSOLUTIZER, useInput = OptBoolean.FALSE) PathAbsolutizer pathAbsolutizer,
    String stringValue
  ) {
    super(stringValue != null ? pathAbsolutizer.absolutize(stringValue) : null);
    Path value = getValue();
    if (value != null && !value.toFile().exists()) {
      throw new IllegalArgumentException(String.format("Path %s not found", value.toString()));
    }
  }
}
