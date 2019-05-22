package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.io.BaseEncoding;

public final class ChecksumField extends Field<byte[]> {
  private static final BaseEncoding CHECKSUM_ENCODING = BaseEncoding.base16();

  @JsonCreator
  public ChecksumField(String stringValue) {
    super(CHECKSUM_ENCODING.decode(stringValue));
  }
}
