// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.spdx;

import javax.annotation.Nullable;
import org.spdx.rdfparser.license.LicenseException;
import org.spdx.rdfparser.license.OrLaterOperator;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;
import org.spdx.rdfparser.license.WithExceptionOperator;

public interface AnyLicenseInfoWalker<T> {
  @Nullable T visitSimpleLicensingInfo(SimpleLicensingInfo simpleLicensingInfo);

  @Deprecated
  default @Nullable T visitOrLaterOperator(OrLaterOperator orLaterOperator) { return null; }

  default @Nullable T visitWithExceptionOperator(WithExceptionOperator withExceptionOperator) { return null; };

  default @Nullable T visitException(LicenseException licenseException) { return null; };

  default @Nullable T visitNoAssertionLicense(SpdxNoAssertionLicense noAssertionLicense)  { return null; };

  default @Nullable T visitNoneLicense(SpdxNoneLicense noneLicense) { return null; };
}
