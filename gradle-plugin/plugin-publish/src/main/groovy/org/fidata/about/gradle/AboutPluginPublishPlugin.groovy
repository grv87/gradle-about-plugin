// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle

import com.gradle.publish.PluginBundleExtension
import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutPluginPublishPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.extensions.configure(PluginBundleExtension) { PluginBundleExtension extension ->
      /*
       * WORKAROUND:
       * Groovy 2.5 don't let us use `with` here. Got error:
       * java.lang.ClassCastException: com.gradle.publish.PluginBundleExtension cannot be cast to groovy.lang.GroovyObject
       * Must be a bug in Groovy
       * <grv87 2018-12-02>
       */
      extension.description = project.version.toString() == '1.0.0' ? project.description : rootProjectConvention.changeLogTxt.get().toString()
      extension.tags = (Collection<String>)projectConvention.tags.get()
      extension.website = projectConvention.websiteUrl.get()
      extension.vcsUrl = rootProjectConvention.vcsUrl.get()
    }
  }
}
