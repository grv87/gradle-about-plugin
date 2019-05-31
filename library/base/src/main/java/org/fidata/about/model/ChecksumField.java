package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.io.BaseEncoding;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

public final class ChecksumField extends Field<byte[]> {
  static final BaseEncoding CHECKSUM_ENCODING = BaseEncoding.base16();

  @JsonCreator
  public ChecksumField(String stringValue) {
    this(CHECKSUM_ENCODING.decode(stringValue));
  }

  public ChecksumField(byte[] value) {
    super(value);
  }

  @Override
  public String toString() {
    return "ChecksumField(super=Field(value=" + CHECKSUM_ENCODING.encode(getValue()) + "))";
  }
}
