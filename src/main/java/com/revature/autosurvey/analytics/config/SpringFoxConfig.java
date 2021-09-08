package com.revature.autosurvey.analytics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableAsync
public class SpringFoxConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("AutoSurvey Analytics API")
                        .title("Analytics API")
                        .version("1.0.0")
                        .build())
                .enable(true)
                .enableUrlTemplating(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.revature.autosurvey.analytics.controller"))
                .paths(PathSelectors.any())
                .build();

    }
}