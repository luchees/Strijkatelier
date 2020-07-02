package com.strike.strijkatelier.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Configuration for the Swagger-UI
 */
@Slf4j
@Configuration
public class SwaggerConfig {

    private final boolean swaggerEnabled;

    public SwaggerConfig(@Value("${framework.swagger.enabled:true}") boolean swaggerEnabled) {
        this.swaggerEnabled = swaggerEnabled;
    }

//    @Primary
//    @Bean
//    public SwaggerResourcesProvider swaggerResourcesProvider() {
//        return () -> {
//            SwaggerResource wsResource = new SwaggerResource();
//            wsResource.setName("Documentation");
//            wsResource.setSwaggerVersion("2.0");
//            wsResource.setLocation("/swagger-config.yml");
//
//            List<SwaggerResource> resources = List.of(wsResource);
//            return resources;
//        };
//    }


    @Bean
    public Docket internalApi() {
        Docket thisDocket;
        thisDocket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("Strijkatelier API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.strike.strijkatelier.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .ignoredParameterTypes()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .alternateTypeRules()
                .enable(swaggerEnabled);
        return thisDocket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Strijkaterlier API")
                .contact(new Contact("Lucas Van den Abbeele", "", ""))
                .description("")
                .version("1.0")
                .build();
    }
}
