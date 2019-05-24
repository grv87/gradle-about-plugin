package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.nio.file.Path;
import org.fidata.utils.PathAbsolutizer;

public class PathField extends Field<Path> {
  static final String PATH_ABSOLUTIZER = "PATH_ABSOLUTIZER";

  @JsonCreator
  public PathField(
    @JacksonInject(value = PATH_ABSOLUTIZER, useInput = OptBoolean.FALSE) PathAbsolutizer pathAbsolutizer,
    String stringValue
  ) {
    super(pathAbsolutizer.absolutize(stringValue));
    if (!getValue().toFile().exists()) {
      throw new IllegalArgumentException(String.format("Path %s not found", getValue().toString()));
    }
  }
}
