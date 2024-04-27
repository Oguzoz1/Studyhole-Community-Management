package com.studyhole.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.studyhole.app.config.OpenApiConfig;


@SpringBootApplication
@EnableAsync
@Import(OpenApiConfig.class)
public class main {

	public static void main(String[] args) {
		SpringApplication.run(main.class, args);
	}

}
