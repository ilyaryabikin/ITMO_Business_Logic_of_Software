package se.ifmo.blos.lab1.configs;

import static io.undertow.UndertowOptions.ENABLE_HTTP2;

import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {

  @Bean
  public UndertowBuilderCustomizer builderCustomizer() {
    return builder -> builder.setServerOption(ENABLE_HTTP2, true);
  }
}
