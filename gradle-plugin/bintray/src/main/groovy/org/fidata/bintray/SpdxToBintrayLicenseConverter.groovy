// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.bintray

import static org.spdx.rdfparser.SpdxRdfConstants.LICENSE_ID_PATTERN
import static org.spdx.rdfparser.license.LicenseInfoFactory.NOASSERTION_LICENSE_NAME
import static org.spdx.rdfparser.license.LicenseInfoFactory.NONE_LICENSE_NAME
import com.google.common.base.Optional
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import groovy.transform.CompileStatic
import java.util.regex.Matcher
import org.fidata.spdx.AnyLicenseInfoWalker
import org.fidata.spdx.SpdxUtils
import org.spdx.rdfparser.license.AnyLicenseInfo
import org.spdx.rdfparser.license.LicenseException
import org.spdx.rdfparser.license.SimpleLicensingInfo
import org.spdx.rdfparser.license.SpdxListedLicense
import org.spdx.rdfparser.license.SpdxNoAssertionLicense
import org.spdx.rdfparser.license.SpdxNoneLicense
import org.spdx.rdfparser.license.WithExceptionOperator

// Another approach: https://github.com/aalmiray/kordamp-gradle-plugins/blob/master/plugins/base-gradle-plugin/src/main/groovy/org/kordamp/gradle/plugin/base/model/LicenseId.groovy

@CompileStatic
final class SpdxToBintrayLicenseConverter {
  // Mapping is made: some exceptions (like WITH operator or BSD-2-Clause-Patent) may be removed
  // But additional limitations (like BSD-3-Clause-No-Nuclear-License) are preserved
  // TODO: write doc
  private static final Map<String, Optional<String>> CONVERSIONS = { ->
    ImmutableMap.Builder<String, Optional<String>> builder = ImmutableMap.builder()
    // Identical conversions
    builder.put 'AFL-2.1', Optional.<String>absent()
    builder.put 'AFL-3.0', Optional.<String>absent()
    builder.put 'Apache-1.0', Optional.<String>absent()
    builder.put 'Apache-1.1', Optional.<String>absent()
    builder.put 'Apache-2.0', Optional.<String>absent()
    builder.put 'APL-1.0', Optional.<String>absent()
    builder.put 'APSL-2.0', Optional.<String>absent()
    builder.put 'BSL-1.0', Optional.<String>absent()
    builder.put 'CC0-1.0', Optional.<String>absent()
    builder.put 'CDDL-1.0', Optional.<String>absent()
    builder.put 'CDDL-1.1', Optional.<String>absent()
    builder.put 'CPAL-1.0', Optional.<String>absent()
    builder.put 'CPL-1.0', Optional.<String>absent()
    builder.put 'CPOL-1.02', Optional.<String>absent()
    builder.put 'EPL-1.0', Optional.<String>absent()
    builder.put 'EPL-2.0', Optional.<String>absent()
    builder.put 'EUDATAGRID', Optional.<String>absent()
    builder.put 'EUPL-1.1', Optional.<String>absent()
    builder.put 'EUPL-1.2', Optional.<String>absent()
    builder.put 'Fair', Optional.<String>absent()
    builder.put 'Frameworx-1.0', Optional.<String>absent()
    builder.put 'GPL-3.0-only', Optional.absent()
    builder.put 'GPL-3.0-or-later', Optional.absent()
    builder.put 'IJG', Optional.<String>absent()
    builder.put 'ImageMagick', Optional.<String>absent()
    builder.put 'ISC', Optional.<String>absent()
    builder.put 'JSON', Optional.<String>absent()
    builder.put 'Libpng', Optional.<String>absent()
    builder.put 'LPPL-1.0', Optional.<String>absent()
    builder.put 'MirOS', Optional.<String>absent()
    builder.put 'MIT', Optional.<String>absent()
    builder.put 'MPL-2.0', Optional.<String>absent()
    builder.put 'MS-PL', Optional.<String>absent()
    builder.put 'MS-RL', Optional.<String>absent()
    builder.put 'Multics', Optional.<String>absent()
    builder.put 'NASA-1.3', Optional.<String>absent()
    builder.put 'NAUMEN', Optional.<String>absent()
    builder.put 'NCSA', Optional.<String>absent()
    builder.put 'NTP', Optional.<String>absent()
    builder.put 'OCLC-2.0', Optional.<String>absent()
    builder.put 'OpenSSL', Optional.<String>absent()
    builder.put 'OSL-3.0', Optional.<String>absent()
    builder.put 'PHP-3.0', Optional.<String>absent()
    builder.put 'PostgreSQL', Optional.<String>absent()
    builder.put 'RPL-1.5', Optional.<String>absent()
    builder.put 'SimPL-2.0', Optional.<String>absent()
    builder.put 'Sleepycat', Optional.<String>absent()
    builder.put 'TMate', Optional.<String>absent()
    builder.put 'Unicode-DFS-2015', Optional.<String>absent()
    builder.put 'Unlicense', Optional.<String>absent()
    builder.put 'UPL-1.0', Optional.<String>absent()
    builder.put 'W3C', Optional.<String>absent()
    builder.put 'WTFPL', Optional.<String>absent()
    builder.put 'Xnet', Optional.<String>absent()
    builder.put 'ZLIB', Optional.<String>absent()
    builder.put 'ZPL-2.0', Optional.<String>absent()
    // Mappings
    builder.put 'AGPL-3.0-only', Optional.of('AGPL-V3')
    builder.put 'AGPL-3.0-or-later', Optional.of('AGPL-V3')
    builder.put 'Artistic-2.0', Optional.of('Artistic-License-2.0')
    builder.put 'AAL', Optional.of('Attribution')
    builder.put 'BSD-4-Clause-UC', Optional.of('BSD')
    builder.put 'BSD-4-Clause', Optional.of('BSD')
    builder.put 'BSD-2-Clause', Optional.of('BSD 2-Clause')
    builder.put 'BSD-2-Clause-FreeBSD', Optional.of('BSD 2-Clause')
    builder.put 'BSD-2-Clause-NetBSD', Optional.of('BSD 2-Clause')
    builder.put 'BSD-2-Clause-Patent', Optional.of('BSD 2-Clause')
    builder.put 'BSD-3-Clause', Optional.of('BSD 3-Clause')
    builder.put 'BSD-3-Clause-LBNL', Optional.of('BSD 3-Clause')
    builder.put 'CATOSL-1.1', Optional.of('CA-TOSL-1.1')
    builder.put 'CECILL-1.0', Optional.of('CeCILL-1')
    builder.put 'CECILL-2.0', Optional.of('CeCILL-2')
    builder.put 'CECILL-2.1', Optional.of('CeCILL-2.1')
    builder.put 'CECILL-B', Optional.of('CeCILL-B')
    builder.put 'CECILL-C', Optional.of('CeCILL-C')
    builder.put 'CUA-OPL-1.0', Optional.of('CUAOFFICE-1.0')
    builder.put 'ECL-2.0', Optional.of('ECL2')
    builder.put 'EFL-2.0', Optional.of('Eiffel-2.0')
    builder.put 'Entessa', Optional.of('Entessa-1.0')
    builder.put 'GPL-2.0-only', Optional.of('GPL-2.0')
    builder.put 'GPL-2.0-or-later', Optional.of('GPL-2.0')
    builder.put 'HPND', Optional.of('Historical')
    builder.put 'IPL-1.0', Optional.of('IBMPL-1.0')
    builder.put 'IPA', Optional.of('IPAFont-1.0')
    builder.put 'LGPL-2.0-only', Optional.of('LGPL-2.0')
    builder.put 'LGPL-2.0-or-later', Optional.of('LGPL-2.0')
    builder.put 'LGPL-2.1-only', Optional.of('LGPL-2.1')
    builder.put 'LGPL-2.1-or-later', Optional.of('LGPL-2.1')
    builder.put 'LGPL-3.0-only', Optional.of('LGPL-3.0')
    builder.put 'LGPL-3.0-or-later', Optional.of('LGPL-3.0')
    builder.put 'LPL-1.02', Optional.of('Lucent-1.02')
    builder.put 'Motosoto', Optional.of('Motosoto-0.9.1')
    builder.put 'MPL-1.1', Optional.of('Mozilla-1.1')
    builder.put 'NGPL', Optional.of('Nethack')
    builder.put 'Nokia', Optional.of('Nokia-1.0a')
    builder.put 'NPOSL-3.0', Optional.of('NOSL-3.0')
    builder.put 'OFL-1.1', Optional.of('Openfont-1.1')
    builder.put 'OGTSL', Optional.of('Opengroup')
    builder.put 'OLDAP-2.8', Optional.of('OpenLDAP')
    builder.put 'CNRI-Python', Optional.of('PythonPL')
    builder.put 'Python-2.0', Optional.of('PythonSoftFoundation')
    builder.put 'QPL-1.0', Optional.of('QTPL-1.0')
    builder.put 'RPSL-1.0', Optional.of('Real-1.0')
    builder.put 'RSCPL', Optional.of('RicohPL')
    builder.put 'SPL-1.0', Optional.of('SUNPublic-1.0')
    builder.put 'Watcom-1.0', Optional.of('Sybase-1.0')
    builder.put 'Vim', Optional.of('VIM License')
    builder.put 'VSL-1.0', Optional.of('VovidaPL-1.0')
    builder.build()
  }()

  private static final Set<String> UNMAPPED_BINTRAY_LICENSES = ImmutableSet.of(
    'GPL-3.0', // Use GPL-3.0-only or GPL-3.0-or-later instead
    'CPOL', // Use CPOL-1.02 instead
    'BSD New', // Alias for BSD 3-Clause
    'BSD Simplified', // Alias for BSD 2-Clause
    'UoI-NCSA', // Alias for NCSA
    // Other variants of MIT/BSD licenses, or missing from SPDX
    'Bouncy-Castle',
    'Codehaus',
    'Copyfree',
    'Day',
    'Day-Addendum',
    'Facebook-Platform',
    'Go',
    'HSQLDB',
    'IU-Extreme-1.1.1',
    'JA-SIG',
    'JTidy',
    'NUnit-2.6.3',
    'NUnit-Test-Adapter-2.6.3',
    'Public Domain',
    'Public Domain - SUN',
    'Scala',
    'wxWindows',
  )

  private static final Set<String> KNOWN_BINTRAY_LICENSES = { ->
    ImmutableSet.Builder<String> builder = ImmutableSet.builder()
    CONVERSIONS.each { String spdxLicenseId, Optional<String> bintrayLicenseId ->
      builder.add bintrayLicenseId.or(spdxLicenseId)
    }
    builder.addAll UNMAPPED_BINTRAY_LICENSES
    builder.build()
  }()

  private static IllegalArgumentException spdxLicenseCantBeExpressedInBintray(String spdxLicenseId) {
    return new IllegalArgumentException("SPDX license $spdxLicenseId can't be expressed in Bintray")
  }

  static List<String> convert(AnyLicenseInfo spdxLicense) {
    return SpdxUtils.walkLicenseInfo(spdxLicense, new AnyLicenseInfoWalker<String>() {
      @Override
      String visitSimpleLicensingInfo(SimpleLicensingInfo simpleLicensingInfo) {
        String spdxLicenseId = simpleLicensingInfo.licenseId
        if (SpdxListedLicense.isInstance(simpleLicensingInfo)) {
          Optional<String> bintrayLicenseId = CONVERSIONS.get(spdxLicenseId)
          if (bintrayLicenseId != null) {
            return bintrayLicenseId.or(spdxLicenseId)
          }
          throw spdxLicenseCantBeExpressedInBintray(spdxLicenseId)
        }
        // Map 1 to 1, assuming user uses private Bintray repo
        // with custom licenses
        Matcher m = LICENSE_ID_PATTERN.matcher(spdxLicenseId)
        return m.matches() ? m.group(1) : spdxLicense
      }

      @Override
      String visitWithExceptionOperator(WithExceptionOperator withExceptionOperator) {
        AnyLicenseInfo license = withExceptionOperator.license
        if (!SpdxListedLicense.isInstance(license)) { return null }
        SpdxListedLicense spdxListedLicense = (SpdxListedLicense)license
        if (spdxListedLicense.licenseId == 'GPL-2.0-only' && withExceptionOperator.exception.licenseExceptionId == 'Classpath-exception-2.0') { return 'GPL-2.0+CE' }
        return null
      }

      @Override
      String visitException(LicenseException licenseException) {
        return null
      }

      @Override
      String visitNoAssertionLicense(SpdxNoAssertionLicense noAssertionLicense) {
        throw spdxLicenseCantBeExpressedInBintray(NOASSERTION_LICENSE_NAME)
      }

      @Override
      String visitNoneLicense(SpdxNoneLicense noneLicense) {
        throw spdxLicenseCantBeExpressedInBintray(NONE_LICENSE_NAME)
      }
    })
  }

  private SpdxToBintrayLicenseConverter() {
    throw new UnsupportedOperationException()
  }
}
