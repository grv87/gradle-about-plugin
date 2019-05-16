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
import lombok.Getter;
import org.fidata.utils.PathAbsolutizer;

public class RelativePathDeserializer extends StdScalarDeserializer<Path> {
  @Getter
  private final PathAbsolutizer pathAbsolutizer;
  public RelativePathDeserializer(PathAbsolutizer pathAbsolutizer) {
    super(Path.class);
    this.pathAbsolutizer = pathAbsolutizer;
  }

  @Override
  public Path deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    final TextNode node = jp.getCodec().readTree(jp);
    final String nodeValue = node.textValue();
    Path relPath;
    try {
      return pathAbsolutizer.absolutize(nodeValue);
    } catch (InvalidPathException e) {
      throw new InvalidFormatException(jp, e.getMessage(), nodeValue, _valueClass);
    }
  }
}
