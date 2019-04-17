package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RelativePathDeserializer extends StdScalarDeserializer<Path> {
  private final Path src;
  public RelativePathDeserializer(File src) {
    super(Path.class);
    this.src = src.toPath();
  }

  @Override
  public Path deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    final TextNode node = jp.getCodec().readTree(jp);
    final String nodeValue = node.textValue();
    Path relPath;
    try {
      relPath = Paths.get(nodeValue);
    } catch (InvalidPathException e) {
      throw new InvalidFormatException(jp, e.getMessage(), nodeValue, _valueClass);
    }
    return src.resolve(relPath).toAbsolutePath();
  }
}
