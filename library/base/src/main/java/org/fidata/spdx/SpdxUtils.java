// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.spdx;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;
import org.spdx.rdfparser.license.OrLaterOperator;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;
import org.spdx.rdfparser.license.WithExceptionOperator;

public final class SpdxUtils {
  /**
   *
   * @param licenseInfo
   * @param walker
   * @param <T>
   * @return
   */
  @SuppressWarnings("deprecation")
  public static <T> List<T> walkLicenseInfo(AnyLicenseInfo licenseInfo, AnyLicenseInfoWalker<T> walker) {
    if (SimpleLicensingInfo.class.isInstance(licenseInfo)) {
      T result = walker.visitSimpleLicensingInfo((SimpleLicensingInfo)licenseInfo);
      return result != null ? ImmutableList.of(result) : ImmutableList.of();
    } else if (OrLaterOperator.class.isInstance(licenseInfo)) {
      OrLaterOperator orLaterOperator = (OrLaterOperator)licenseInfo;
      T result = walker.visitOrLaterOperator(orLaterOperator);
      if (result != null) {
        return ImmutableList.of(result);
      }
      result = walker.visitSimpleLicensingInfo(orLaterOperator.getLicense());
      return result != null ? ImmutableList.of(result) : ImmutableList.of();
    } else if (WithExceptionOperator.class.isInstance(licenseInfo)) {
      WithExceptionOperator withExceptionOperator = (WithExceptionOperator)licenseInfo;
      T result = walker.visitWithExceptionOperator(withExceptionOperator);
      if (result != null) {
        return ImmutableList.of(result);
      }
      ImmutableList.Builder<T> resultBuilder = ImmutableList.builder();
      resultBuilder.addAll(walkLicenseInfo(withExceptionOperator.getLicense(), walker));
      T subResult = walker.visitException(withExceptionOperator.getException());
      if (subResult != null) {
        resultBuilder.add(subResult);
      }
      return resultBuilder.build();
    } else if (LicenseSet.class.isInstance(licenseInfo)) {
      AnyLicenseInfo[] members = ((LicenseSet)licenseInfo).getMembers();
      ImmutableList.Builder<T> resultBuilder = ImmutableList.builderWithExpectedSize(members.length);
      for (AnyLicenseInfo member : members) {
        resultBuilder.addAll(walkLicenseInfo(member, walker));
      }
      return resultBuilder.build();
    } else if (SpdxNoAssertionLicense.class.isInstance(licenseInfo)) {
      T result = walker.visitNoAssertionLicense((SpdxNoAssertionLicense)licenseInfo);
      return result != null ? ImmutableList.of(result) : ImmutableList.of();
    } else if (SpdxNoneLicense.class.isInstance(licenseInfo)) {
      T result = walker.visitNoneLicense((SpdxNoneLicense)licenseInfo);
      return result != null ? ImmutableList.of(result) : ImmutableList.of();
    } else {
      throw new UnsupportedOperationException(String.format("Can't walk through unknown subclass of AnyLicenseInfo: %s", licenseInfo.getClass().getName()));
    }
  }
  
  private SpdxUtils() {
    throw new UnsupportedOperationException();
  }
}
