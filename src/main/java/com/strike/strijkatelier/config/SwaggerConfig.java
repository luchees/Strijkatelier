package com.strike.strijkatelier.config;

import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDateTime;

/**
 * Configuration for the Swagger-UI
 */
@Slf4j
@Configuration
public class SwaggerConfig {

    private final TypeResolver typeResolver;
    private final boolean swaggerEnabled;

    public SwaggerConfig(@Value("${framework.swagger.enabled:true}") boolean swaggerEnabled) {
        this.typeResolver = new TypeResolver();
        this.swaggerEnabled = swaggerEnabled;
    }

    @Bean
    public Docket internalApi() {
        Docket thisDocket;
        thisDocket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("Strijkatelier API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage( "com.strike.strijkatelier.controller" ))
                .paths(PathSelectors.any())
                .build()
                .forCodeGeneration(true)
                .ignoredParameterTypes()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .alternateTypeRules()
                .enable(swaggerEnabled);
        return thisDocket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Strijkaterlier API")
                .contact(new Contact("Lucas Vanden abbeele", "", ""))
                .description("")
                .version("1.0")
                .build();
    }
}
