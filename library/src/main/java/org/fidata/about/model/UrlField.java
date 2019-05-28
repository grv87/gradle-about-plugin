package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.ToString;

@ToString
public final class UrlField extends Field<URL> {
  @JsonCreator
  public UrlField(String stringValue) throws MalformedURLException {
    this(new URL(stringValue));
  }

  public UrlField(URL urlValue) {
    super(urlValue);
  }
}
