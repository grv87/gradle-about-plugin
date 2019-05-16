package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import org.fidata.utils.PathRelativizer;

public class RelativePathSerializer extends StdScalarSerializer<Path> {
  private final PathRelativizer pathRelativizer;
  public RelativePathSerializer(PathRelativizer pathRelativizer) {
    super(Path.class);
    this.pathRelativizer = pathRelativizer;
  }

  @Override
  public void serialize(Path value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(pathRelativizer.relativize(value).toString());
  }
}
