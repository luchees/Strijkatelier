package com.strike.strijkatelier.config.constant;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 8/7/2020 AD
 */
public class AuthWhiteList {

    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/api-docs",
            "/actuator",
            "/actuator/**",
            "/webjars/**",
            "/api/register",
            "/api/login"
    };

}

