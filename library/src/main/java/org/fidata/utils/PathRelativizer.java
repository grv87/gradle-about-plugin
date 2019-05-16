package org.fidata.utils;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import lombok.Getter;
import lombok.ToString;

/**
 * Class to convert absolute paths to relative to some base directory
 */
@ToString
public final class PathRelativizer {
  @Getter
  private final Path baseDir;
  private final FileSystem baseDirFileSystem;
  private final Path baseDirRoot;

  public PathRelativizer(Path baseDir) {
    this.baseDir = baseDir;
    this.baseDirFileSystem = this.baseDir.getFileSystem();
    this.baseDirRoot = this.baseDir.getRoot();
  }

  public PathRelativizer(File baseDir) {
    this(baseDir.toPath());
  }

  public Path relativize(Path absPath) {
    if (
      !baseDirFileSystem.equals(absPath.getFileSystem()) ||
      !baseDirRoot.equals(absPath.getRoot())
    ) {
      throw new IllegalArgumentException("Path with different file system or root cannot be relativized");
    }
    return baseDir.relativize(absPath);
  }
}
