// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * An ABOUT file field. The initial value is a string. Subclasses can and
 * will alter the value type as needed.
 */
@EqualsAndHashCode
@ToString
public abstract class Field<T> {
  @Getter
  private final T value;

  public Field(T value) {
    this.value = value;
  }
}
