package org.fidata.spdx;

import org.spdx.rdfparser.license.LicenseException;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;

public interface AnyLicenseInfoWalker {
  void visitSimpleLicensingInfo(SimpleLicensingInfo simpleLicensingInfo);

  default void visitException(LicenseException licenseException) {}

  default void visitNoAssertionLicense(SpdxNoAssertionLicense noAssertionLicense) {}

  default void visitNoneLicense(SpdxNoneLicense noneLicense) {}
}
