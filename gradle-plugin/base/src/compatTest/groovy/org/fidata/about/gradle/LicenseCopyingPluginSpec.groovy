// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification

class LicenseCopyingPluginSpec extends Specification {
  void "copies license files to resources"() {
    given:

    when: 'processResources task is run'
    GradleRunner.create()
      .withGradleVersion(System.getProperty('compat.gradle.version'))
      .withProjectDir(testProjectDir)
      .withArguments('processResources', '--full-stacktrace')
      .withPluginClasspath()
      .build()
    then: 'LICENSE.txt is copied to build/changelog directory'
    new File(testProjectDir, 'build/changelog/CHANGELOG.md').exists()

  }

  void "copies notice file to resources"() {
    given:

    when:

    then:

  }

  void 'copies license file into resources META-INF directory'() {
    given: 'license file'
    String license = 'LICENSE'
    new File(testProjectDir, license).text = 'Dummy license file'

    when:
    GradleRunner.create()
      .withGradleVersion(System.getProperty('compat.gradle.version'))
      .withProjectDir(testProjectDir)
      .withArguments('processResources', '--full-stacktrace')
      .withPluginClasspath()
      .forwardOutput()
      .build()

    then: 'resources META-INF directory contains license file'
    new File(testProjectDir, "build/resources/main/META-INF/$license").text == 'Dummy license file' // TODO

    (success = true) != null
  }

}
