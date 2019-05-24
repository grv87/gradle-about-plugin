package org.fidata.spdx;

import org.spdx.rdfparser.license.LicenseException;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;

public interface AnyLicenseInfoWalker {
  void processSimpleLicensingInfo(SimpleLicensingInfo simpleLicensingInfo);

  default void processException(LicenseException licenseException) {}

  default void processNoAssertionLicense(SpdxNoAssertionLicense noAssertionLicense) {}

  default void processNoneLicense(SpdxNoneLicense noneLicense) {}
}
