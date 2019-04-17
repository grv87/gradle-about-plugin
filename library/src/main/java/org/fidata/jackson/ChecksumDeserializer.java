package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.io.BaseEncoding;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ChecksumDeserializer extends StdScalarDeserializer<byte[]> {
  private static final BaseEncoding ENCODING = BaseEncoding.base16();

  public ChecksumDeserializer(File src) {
    super(byte[].class);
  }

  @Override
  public byte[] deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    TextNode node = jp.getCodec().readTree(jp);
    final String nodeValue = node.textValue();
    try {
      return ENCODING.decode(node.textValue());
    } catch (IllegalArgumentException e) {
      throw new InvalidFormatException(jp, e.getMessage(), nodeValue, _valueClass);
    }
  }
}
