package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public class RelativePathSerializer extends StdScalarSerializer<Path> {
  private final Path res;
  private final FileSystem resFileSystem;
  private final Path resRoot;
  public RelativePathSerializer(File res) {
    super(Path.class);
    this.res = res.toPath();
    this.resFileSystem = this.res.getFileSystem();
    this.resRoot = this.res.getRoot();
  }

  @Override
  public void serialize(Path value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    if (
      !resFileSystem.equals(value.getFileSystem()) ||
      !resRoot.equals(value.getRoot())
    ) {
      throw new IllegalArgumentException("Path with different file system or root cannot be serialized relatively");
    }
    gen.writeString(res.relativize(value).toString());
  }
}
