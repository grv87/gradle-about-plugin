package org.fidata.maven;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.scm.manager.ScmManager;


public final class MavenUtils {
  private static final char DELIMITER = SystemUtils.IS_OS_WINDOWS ? '|' : ':';

  public static URL constructDeveloperConnectionUrl(String tool, String repository, String path, String tag, String branch, String revision) {
    try {
      if ("bazaar".equals(tool)) {
        return new URL("scm:bazaar" + DELIMITER + repository);
      } else if ("cvs".equals(tool)) {
        return new URL("scm:cvs" + DELIMITER + repository); // TODO
      } else if ("git".equals(tool)) {
        return new URL("scm:git" + DELIMITER + repository); // TODO
      }
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(String.format("Cannot connect developer connection url from arguments:" +
        " tool = %s, repository = %s, path = %s, tag = %s, branch = %s, revision = %s",
        tool, repository, path, tag, branch, revision), e);
    }
  }


  private MavenUtils() {
    throw new UnsupportedOperationException();
  }
}
