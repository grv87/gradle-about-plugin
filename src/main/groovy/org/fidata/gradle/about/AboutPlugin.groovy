package org.fidata.gradle.about

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.guice.ObjectMapperModule
import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutPlugin implements Plugin<Project> {
  public static final String ABOUT_EXTENSION_NAME = 'about'

  @Override
  void apply(Project project) {
    final Injector injector = Guice.createInjector(
      new ObjectMapperModule(),
      new Module() {
        @Override
        void configure(Binder binder) {
          binder.bind(Project).toInstance(project)
        }
      }
    )
    final ObjectMapper objectMapper = injector.getInstance(ObjectMapper)

    final About about = objectMapper.readValue(project.file("${ project.name }.ABOUT"), About)

    project.extensions.add ABOUT_EXTENSION_NAME, about
  }
}
