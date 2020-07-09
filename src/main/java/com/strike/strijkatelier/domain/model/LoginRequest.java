package com.strike.strijkatelier.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotEmpty;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 8/7/2020 AD
 */

@ApiModel(value = "LoginRequest", description = "Request used for login")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {

    @NotEmpty
    @MatchesPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotEmpty
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "String", example = "jon.doe@gmail.com", notes = "Email of the user")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "Lucas", notes = "Password of the user")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
