package se.ifmo.blos.lab3.configs;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalServiceConfig {

  @Bean
  public RestTemplate externalServiceRestTemplate(final RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(20))
        .setReadTimeout(Duration.ofSeconds(30))
        .rootUri("http://core:8090/")
        .build();
  }
}
