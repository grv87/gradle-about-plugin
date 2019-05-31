package org.fidata.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;

/**
 * Class to convert paths relative to some base directory to absolute
 */
public final class PathAbsolutizer {
  @Getter
  private final Path baseDir;

  public PathAbsolutizer(Path baseDir) {
    this.baseDir = baseDir;
  }

  public PathAbsolutizer(File baseDir) {
    this(baseDir.toPath());
  }

  public Path absolutize(Path relPath) {
    return baseDir.resolve(relPath).toAbsolutePath();
  }

  /**
   *
   * @param relPath
   * @throws java.nio.file.InvalidPathException
   * @return
   */
  public Path absolutize(String relPath) {
    return absolutize(Paths.get(relPath));
  }
}
