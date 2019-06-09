// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.jackson;

import com.fasterxml.jackson.core.Version;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionParser {
  private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)(.(\\d+))?");

  /**
   * Parses simple string version, like <code>3.1</code> or <code>2.1.5</code>
   *
   * @param s version string
   * @return Jackson's Version object
   */
  public static Version parse(String s) {
    Matcher m = VERSION_PATTERN.matcher(s);
    if (!m.matches()) {
      throw new IllegalArgumentException(String.format("Invalid version string: %s", s));
    }
    return new Version(
      Integer.valueOf(m.group(1)),
      Integer.valueOf(m.group(2)),
      m.group(4) != null ? Integer.valueOf(m.group(4)) : 0,
      "",
      null,
      null
    );
  }

  private VersionParser() {
    throw new UnsupportedOperationException();
  }
}
