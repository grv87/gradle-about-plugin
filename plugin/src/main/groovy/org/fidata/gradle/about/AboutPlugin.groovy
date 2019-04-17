package org.fidata.gradle.about

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.guice.ObjectMapperModule
import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import groovy.transform.CompileStatic
import java.lang.reflect.Constructor
import java.nio.file.Path
import java.nio.file.Paths
import org.fidata.about.About
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
          Constructor<ObjectMapper> objectMapperConstructor = ObjectMapper.getConstructor(JsonFactory)
          binder.bind(ObjectMapper).toConstructor(objectMapperConstructor)
          binder.bind(JsonFactory).to(YAMLFactory) // TODO
          binder.bind(Project).toInstance(project)
        }
      }
    )
    final ObjectMapper objectMapper = injector.getInstance(ObjectMapper)
    final deserializerModule = new SimpleModule()
    /*deserializerModule.addDeserializer(new StdDeserializer<Path>(Path) {
      @Override
      Path deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        return Paths.get()
      }
    })
    objectMapper.registerModule(new SimpleModule() {
    })*/

    final About about = objectMapper.readValue(project.file("${ project.name }.ABOUT"), About)

    project.extensions.add ABOUT_EXTENSION_NAME, about

    /*public static ObjectMapper configureMapper(ObjectMapper mapper) {
      SimpleModule m = new SimpleModule("PathToString");
      m.addSerializer(Path, new ToStringSerializer());
      mapper.registerModule(m);
      return mapper;
    }*/
  }
}
