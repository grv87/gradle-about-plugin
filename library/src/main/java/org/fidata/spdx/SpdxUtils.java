package org.fidata.spdx;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;
import org.spdx.rdfparser.license.OrLaterOperator;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;
import org.spdx.rdfparser.license.WithExceptionOperator;

public final class SpdxUtils {
  public static void walkLicenseInfo(AnyLicenseInfo licenseInfo, AnyLicenseInfoWalker walker) {
    if (SimpleLicensingInfo.class.isInstance(licenseInfo)) {
      walker.processSimpleLicensingInfo((SimpleLicensingInfo)licenseInfo);
    } else if (OrLaterOperator.class.isInstance(licenseInfo)) {
      walker.processSimpleLicensingInfo(((OrLaterOperator)licenseInfo).getLicense());
    } else if (WithExceptionOperator.class.isInstance(licenseInfo)) {
      WithExceptionOperator withExceptionOperator = (WithExceptionOperator)licenseInfo;
      walkLicenseInfo(withExceptionOperator.getLicense(), walker);
      walker.processException(withExceptionOperator.getException());
    } else if (LicenseSet.class.isInstance(licenseInfo)) {
      AnyLicenseInfo[] members = ((LicenseSet)licenseInfo).getMembers();
      for (AnyLicenseInfo member : members) {
        walkLicenseInfo(member, walker);
      }
    } else if (SpdxNoAssertionLicense.class.isInstance(licenseInfo)) {
      walker.processNoAssertionLicense((SpdxNoAssertionLicense)licenseInfo);
    } else if (SpdxNoneLicense.class.isInstance(licenseInfo)) {
      walker.processNoneLicense((SpdxNoneLicense)licenseInfo);
    } else {
      throw new UnsupportedOperationException(String.format("Can't walk through unknown subclass of AnyLicenseInfo: %s", licenseInfo.getClass().getName()));
    }
  }
  
  private SpdxUtils() {
    throw new UnsupportedOperationException();
  }
}
