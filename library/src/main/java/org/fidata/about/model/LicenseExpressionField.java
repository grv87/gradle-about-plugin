package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.atlas.lib.ListUtils;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

public final class LicenseExpressionField extends Field<AnyLicenseInfo> {
  @JsonCreator
  public LicenseExpressionField(String stringValue) throws InvalidLicenseStringException {
    this(LicenseInfoFactory.parseSPDXLicenseString(stringValue));
  }
  public LicenseExpressionField(AnyLicenseInfo licenseInfo) {
    super(licenseInfo);
    // TODO: add license files
    final List<String> validationErrors = licenseInfo.verify();
    if (!validationErrors.isEmpty()) {
      throw new IllegalArgumentException(String.format("License '%s' is not valid. Validation errors: %s", licenseInfo, validationErrors.toString()));
    }
  }
}
