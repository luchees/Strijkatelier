package com.strike.strijkatelier.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.strike.strijkatelier.task" })
public class SpringTaskConfig {

}
