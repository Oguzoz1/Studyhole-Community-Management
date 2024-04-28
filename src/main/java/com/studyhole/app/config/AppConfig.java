package com.studyhole.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    @NotNull
    private String url;
}
