package com.studyhole.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI studyholeAPI(){
        return new OpenAPI().info(new Info()
        .title("Studyhole API")
        .description("Studyhole API documentation.")
        .version("v0.1"));
    }
}
