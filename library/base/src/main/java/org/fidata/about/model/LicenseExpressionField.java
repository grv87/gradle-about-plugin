package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

@ToString(callSuper = true)
public final class LicenseExpressionField extends Field<AnyLicenseInfo> {
  /*
   * CAVEAT:
   * We have to clone original value
   * since it is mutable
   * <grv87 2019-05-27>
   */
  @Override
  public AnyLicenseInfo getValue() {
    return getOriginalValue().clone();
  }

  AnyLicenseInfo getOriginalValue() {
    return super.getValue();
  }

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public LicenseExpressionField(String stringValue) throws InvalidLicenseStringException {
    super(LicenseInfoFactory.parseSPDXLicenseString(stringValue));
  }
  public LicenseExpressionField(AnyLicenseInfo licenseInfo) {
    /*
     * CAVEAT:
     * Defense copying
     * <grv87 2019-06-05>
     */
    super(licenseInfo.clone());
  }
}
