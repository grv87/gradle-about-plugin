package org.fidata.about.gradle

import groovy.transform.CompileStatic
import org.fidata.about.maven.MavenAbout

@CompileStatic
class MavenAboutPlugin extends AbstractAboutPlugin<MavenAbout> {

  @Override
  protected MavenAbout readFromFile(File src) {
    MavenAbout.readFromFile(src)
  }
}
