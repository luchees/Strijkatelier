package com.strike.strijkatelier.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 8/7/2020 AD
 */

@ApiModel(value = "LoginResponse", description = "Response of login")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "JWT token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
