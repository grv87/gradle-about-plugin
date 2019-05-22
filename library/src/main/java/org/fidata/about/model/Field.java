package org.fidata.about.model;

import lombok.Getter;

/**
 * An ABOUT file field. The initial value is a string. Subclasses can and
 * will alter the value type as needed.
 */
public abstract class Field<T> {
  @Getter
  private final T value;

  public Field(T value) {
    this.value = value;
  }

}
