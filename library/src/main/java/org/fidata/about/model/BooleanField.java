package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Locale;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

@ToString(callSuper = true)
public final class BooleanField extends Field<Boolean> {
  public static final BooleanField FALSE = new BooleanField(false);
  public static final BooleanField TRUE = new BooleanField(true);

  BooleanField(boolean booleanValue) {
    super(booleanValue);
  }

  private static final String[] TRUE_FLAGS = {"yes", "y", "true", "x"};
  private static final String[] FALSE_FLAGS = {"no", "n", "false"};
  private static final String[] FLAG_VALUES;
  static {
    final int trueFlagsLen = TRUE_FLAGS.length;
    final int falseFlagsLen = FALSE_FLAGS.length;
    FLAG_VALUES = new String[trueFlagsLen + falseFlagsLen];
    System.arraycopy(TRUE_FLAGS, 0, FLAG_VALUES, 0, trueFlagsLen);
    System.arraycopy(FALSE_FLAGS, 0, FLAG_VALUES, trueFlagsLen, falseFlagsLen);
  }

  @JsonCreator
  public static BooleanField of(String stringValue) {
    stringValue = stringValue.toLowerCase(Locale.ROOT);
    if (ArrayUtils.contains(TRUE_FLAGS, stringValue)) {
      return TRUE;
    } else if (ArrayUtils.contains(FALSE_FLAGS, stringValue)) {
      return FALSE;
    }
    throw new IllegalArgumentException(String.format("Invalid flag value: '%s' is not one of: %s", stringValue, Arrays.toString(FLAG_VALUES)));
  }
}
