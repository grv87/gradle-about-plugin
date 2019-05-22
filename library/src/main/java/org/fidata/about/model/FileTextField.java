package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.nio.file.Path;
import org.fidata.utils.PathAbsolutizer;

public final class FileTextField extends Field<Path> {
  static final String PATH_ABSOLUTIZER = "PATH_ABSOLUTIZER";

  @JsonCreator
  public FileTextField(@JacksonInject(PATH_ABSOLUTIZER) PathAbsolutizer pathAbsolutizer, String stringValue) {
    super(pathAbsolutizer.absolutize(stringValue));
  }
}
