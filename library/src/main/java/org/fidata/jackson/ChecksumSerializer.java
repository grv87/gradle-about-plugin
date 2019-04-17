package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.google.common.io.BaseEncoding;
import java.io.IOException;

public class ChecksumSerializer extends StdScalarSerializer<byte[]> {
  private static final BaseEncoding ENCODING = BaseEncoding.base16();

  public ChecksumSerializer() {
    super(byte[].class);
  }

  @Override
  public void serialize(byte[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(ENCODING.encode(value));
  }
}
