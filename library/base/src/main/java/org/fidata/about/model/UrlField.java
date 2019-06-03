package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import lombok.ToString;
import org.fidata.utils.DummyURLStreamHandler;

@ToString(callSuper = true)
public final class UrlField extends Field<URL> {
  private static URLStreamHandler URL_STREAM_HANDLER = new DummyURLStreamHandler();

  @JsonCreator
  public UrlField(String stringValue) throws MalformedURLException {
    this(new URL(null, stringValue, URL_STREAM_HANDLER));
  }

  public UrlField(URL urlValue) {
    super(urlValue);
  }
}
