package org.fidata.about.model;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.fidata.about.model.FileTextField.PATH_ABSOLUTIZER;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.net.MalformedURLException;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.fidata.utils.PathAbsolutizer;

@JsonDeserialize(builder = AbstractFieldSet.AbstractFieldSetBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractFieldSet {
  protected static final String FILE_FIELD_SUFFIX = "_file";
  protected static final String URL_FIELD_SUFFIX = "_url";

  @Singular
  private final Map<String, ? extends Field> fields;

  private String getExtensionFieldName(String extensionPrefix, String name) {
    return extensionPrefix + '_' + name;
  }

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String name) {
    return (FileTextField)fields.get(name + FILE_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String extensionPrefix, String name) {
    return getFile(getExtensionFieldName(extensionPrefix, name));
  }

  /**
   * Extension fields referencing URLs
   */
  public final UrlField getUrl(String name) {
    return (UrlField)fields.get(name + URL_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing URLs
   */
  public final UrlField getUrl(String extensionPrefix, String name) {
    return getUrl(getExtensionFieldName(extensionPrefix, name));
  }

  /**
   * Other (than file and URL) extension fields
   */
  public final StringField getString(String name) {
    return (StringField)fields.get(name);
  }

  /**
   * Other (than file and URL) extension fields
   */
  public final StringField getString(String extensionPrefix, String name) {
    return getString(getExtensionFieldName(extensionPrefix, name));
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static abstract class AbstractFieldSetBuilder<C extends AbstractFieldSet, B extends AbstractFieldSetBuilder<C, B>> {
    @Setter
    @JacksonInject(PATH_ABSOLUTIZER)
    private PathAbsolutizer pathAbsolutizer;

    @JsonAnySetter
    protected final void unknownField(String name, String value) throws MalformedURLException {
      if (endsWithIgnoreCase(name, FILE_FIELD_SUFFIX)) {
        field(name, new FileTextField(pathAbsolutizer, value));
      } else if (endsWithIgnoreCase(name, URL_FIELD_SUFFIX)) {
        field(name, new UrlField(value));
      } else if (!parseUnknownField(name, value)) {
        field(name, new StringField(value));
      }
    }

    protected boolean parseUnknownField(String name, String value) {
      return false;
    }
  }

  protected static abstract class AbstractFieldSetBuilderImpl extends AbstractFieldSetBuilder<AbstractFieldSet, AbstractFieldSetBuilderImpl> {}
}
