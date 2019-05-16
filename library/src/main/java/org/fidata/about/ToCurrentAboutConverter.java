package org.fidata.about;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.fidata.about.About.FILE_FIELD_SUFFIX;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;
import java.util.Iterator;
import org.fidata.jackson.VersionParser;

public class ToCurrentAboutConverter implements VersionedModelConverter {
  private static final Version VERSION_2_0 = new Version(2, 0, 0, "", null, null);
  // TODO: private static final Version VERSION_3_0 = new Version(3, 0, 0, "", null, null);
  private static final Version VERSION_3_1 = new Version(3, 1, 0, "", null, null);

  @Override
  public ObjectNode convert(ObjectNode modelData, String modelVersion, String targetModelVersion, JsonNodeFactory nodeFactory) {
    Version version = VersionParser.parse(modelVersion);
    Version targetVersion = VersionParser.parse(targetModelVersion);

    if (targetVersion.compareTo(VERSION_3_1) >= 0 && version.compareTo(VERSION_3_1) <= 0) {
      // TODO: test that case is ignored here
      modelData.put("homepage_url", modelData.get("home_url").textValue());
      // Making all paths starting with / relative
      Iterator<String> fieldNamesIterator = modelData.fieldNames();
      String fieldName;
      while ((fieldName = fieldNamesIterator.next()) != null) {
        if (endsWithIgnoreCase(fieldName, FILE_FIELD_SUFFIX)) {
          String fieldValue = modelData.get(fieldName).textValue();
          if (fieldValue.startsWith("/")) {
            modelData.set(fieldName, new TextNode("." + fieldValue));
          }
        }
      }
    }

    if (targetVersion.compareTo(VERSION_2_0) >= 0 && version.compareTo(VERSION_2_0) <= 0) {
      modelData.put("license_file", modelData.get("license_text_file").textValue());
      modelData.put("license_expression", modelData.get("license_spdx").textValue());
    }

    return modelData;
  }
}
