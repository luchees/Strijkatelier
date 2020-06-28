package com.strike.strijkatelier.config;

import com.strike.strijkatelier.security.*;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {
    // beans

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

}