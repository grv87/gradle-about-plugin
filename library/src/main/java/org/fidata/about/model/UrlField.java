package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.MalformedURLException;
import java.net.URL;

public final class UrlField extends Field<URL> {
  @JsonCreator
  public UrlField(String stringValue) throws MalformedURLException {
    super(new URL(stringValue));
  }
}
