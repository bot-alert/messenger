package com.ashishkhatiwada.chat.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
