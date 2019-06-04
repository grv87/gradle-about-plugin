package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.ToString;

@ToString(callSuper = true)
public final class UrlField extends Field<URI> {
  public static final UrlField NULL = new UrlField((URI)null);

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public UrlField(String stringValue) throws URISyntaxException {
    this(new URI(stringValue));
  }

  public UrlField(URI urlValue) {
    super(urlValue);
    if (urlValue != null && urlValue.getScheme() == null) {
      throw new IllegalArgumentException(String.format("URL should have scheme specified: %s", urlValue.toString()));
    }
  }
}
