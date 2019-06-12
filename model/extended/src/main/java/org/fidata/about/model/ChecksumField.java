// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.io.BaseEncoding;

public final class ChecksumField extends Field<byte[]> {
  public static final ChecksumField NULL = new ChecksumField((byte[])null);

  static final BaseEncoding CHECKSUM_ENCODING = BaseEncoding.base16();

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
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
