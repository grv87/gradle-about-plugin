package org.fidata.about.model;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.fidata.about.model.FileTextField.PATH_ABSOLUTIZER;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.fidata.utils.PathAbsolutizer;

@JsonDeserialize(builder = AbstractFieldSet.AbstractFieldSetBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractFieldSet {
  protected static final String FILE_FIELD_SUFFIX = "_file";
  protected static final String URL_FIELD_SUFFIX = "_url";

  @Getter
  @Singular
  private final Map<String, ?> customFields;

  private String getExtensionFieldName(String extensionPrefix, String name) {
    return extensionPrefix + '_' + name;
  }

  /**
   * Extension fields referencing files
   */
  public final FileTextField getFile(String name) {
    return (FileTextField)customFields.get(name + FILE_FIELD_SUFFIX);
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
    return (UrlField)customFields.get(name + URL_FIELD_SUFFIX);
  }

  /**
   * Extension fields referencing URLs
   */
  public final UrlField getUrl(String extensionPrefix, String name) {
    return getUrl(getExtensionFieldName(extensionPrefix, name));
  }

  /**
   * String extension fields
   */
  public final StringField getString(String name) {
    return (StringField)customFields.get(name);
  }

  /**
   * String extension fields
   */
  public final StringField getString(String extensionPrefix, String name) {
    return getString(getExtensionFieldName(extensionPrefix, name));
  }

  /**
   * Structured extension fields
   */
  public final Object getObject(String name) {
    return customFields.get(name);
  }

  /**
   * Structured extension fields
   */
  public final Object getObject(String extensionPrefix, String name) {
    return getObject(getExtensionFieldName(extensionPrefix, name));
  }

  private static final Pattern IS_VALID_NAME = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");

  private static boolean isValidName(String name) {
    return IS_VALID_NAME.matcher(name).matches();
  }

  @JsonPOJOBuilder(withPrefix = "", buildMethodName = "validate")
  public static abstract class AbstractFieldSetBuilder<C extends AbstractFieldSet, B extends AbstractFieldSetBuilder<C, B>> {
    @Setter
    @JacksonInject(PATH_ABSOLUTIZER)
    private PathAbsolutizer pathAbsolutizer;

    @JsonAnySetter
    protected final void unknownField(String name, Object value) throws MalformedURLException {
      name = name.toLowerCase(Locale.ROOT);
      if (!isValidName(name)) {
        // TODO: Fix message
        throw new IllegalArgumentException(String.format("Field name: '%s' contains illegal name characters: " +
          "0 to 9, a to z, A to Z and _.", name));
      }
      if (endsWithIgnoreCase(name, FILE_FIELD_SUFFIX)) {
        customField(name, new FileTextField(pathAbsolutizer, (String)value));
      } else if (endsWithIgnoreCase(name, URL_FIELD_SUFFIX)) {
        customField(name, new UrlField((String)value));
      } else if (!parseUnknownField(name, value)) {
        if (value == null) {
          // Default behavior of aboutcode-toolkit
          // Note that Guava's ImmutableMap anyway doesn't accept nulls
          value = new StringField("");
        } else if (String.class.isInstance(value)) {
          // Note: Jackson already trimmed trailing spaces
          // on each line except the last one
          value = new StringField(StringUtils.stripEnd((String)value, null));
        }
        customField(name, value);
      }
    }

    protected boolean parseUnknownField(String name, Object value) {
      return false;
    }

    // TODO: Try to get rid of it
    public final C validate() {
      doValidate();
      return build();
    }

    protected void doValidate() {}
  }

  protected static abstract class AbstractFieldSetBuilderImpl extends AbstractFieldSetBuilder<AbstractFieldSet, AbstractFieldSetBuilderImpl> {}
}
