GOAL for gradle-about-plugin
============================

Develop:
1. Java library
2. Gradle plugin

that are able to parse ABOUT files according to specification
(except when ABOUT specification contradicts YAML specification).

Library should be plain Java (no Groovy).

Gradle plugin should also be able to run:
1. `about check`
2. `about attrib`

NOT GOALS
=========
1. Java library might not be able to run full validation of ABOUT file
(run of `about check` does that).
2. Saving (serialization) of ABOUT files