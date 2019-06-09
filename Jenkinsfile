// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
//noinspection GroovyUnusedAssignment
@SuppressWarnings(['UnusedVariable', 'NoDef', 'VariableTypeRequired'])
@Library('jenkins-pipeline-shared-library@v3.0.1') dummy

defaultJvmPipeline(
  timeouts: [
    Test: 10,
  ],
  tests: [
    'test',
    'functionalTest',
  ].toSet(),
  gradlePlugin: Boolean.TRUE
)
