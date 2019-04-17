package org.fidata.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

public class SpdxAnyLicenseInfoDeserializer extends StdScalarDeserializer<AnyLicenseInfo> {
  public SpdxAnyLicenseInfoDeserializer() {
    super(AnyLicenseInfo.class);
  }

  @Override
  public AnyLicenseInfo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    TextNode node = jp.getCodec().readTree(jp);
    String nodeValue = node.textValue();
    try {
      return LicenseInfoFactory.parseSPDXLicenseString(nodeValue);
    } catch (InvalidLicenseStringException e) {
      throw new InvalidFormatException(jp, e.getMessage(), nodeValue, _valueClass);
    }
  }
}
