package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonDeserialize(builder = License.Builder.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
public final class License {
  /**
   * The license key for the component
   */
  @Getter
  private final String key;

  /**
   * The license short name for the license
   */
  @Getter
  private final String name;

  /**
   * License file that applies to the component
   *
   * For example, the name of a license file such as LICENSE or COPYING file extracted from a downloaded archive
   */
  @Getter
  private final FileTextField file;

  /**
   * URL to the license text for the component
   */
  @Getter
  private final UrlField url;

  @JsonPOJOBuilder(withPrefix = "")
  public final static class Builder { }
}
