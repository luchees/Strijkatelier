package com.strike.strijkatelier.spring;

import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.session.*;
import org.springframework.web.context.request.*;

@Configuration
public class TestIntegrationConfig {

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {

        return new RequestContextListener();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
