// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.jvm

import com.gradle.publish.PluginBundleExtension
import groovy.transform.CompileStatic
import org.fidata.about.gradle.extended.ExtendedAboutPlugin
import org.fidata.about.model.StringField
import org.fidata.about.model.extended.ExtendedAbout
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutPluginPublishPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.plugins.withType(ExtendedAboutPlugin) { ExtendedAboutPlugin aboutPlugin ->
      ExtendedAbout about = aboutPlugin.about
      project.extensions.configure(PluginBundleExtension) { PluginBundleExtension extension ->
        /*
         * WORKAROUND:
         * Groovy 2.5 don't let us use `with` here. Got error:
         * java.lang.ClassCastException: com.gradle.publish.PluginBundleExtension cannot be cast to groovy.lang.GroovyObject
         * Must be a bug in Groovy
         * <grv87 2018-12-02>
         */
        extension.description = about.description.value
        extension.tags = about.keywords.collect { StringField keywordField ->
          keywordField.value
        }
        extension.website = about.homepageUrl
        extension.vcsUrl = about.vcsRepository // TODO
        null
      }
    }
  }
}
