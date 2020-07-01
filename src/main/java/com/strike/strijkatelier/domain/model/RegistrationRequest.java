package com.strike.strijkatelier.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.strike.strijkatelier.validation.PasswordMatches;
import com.strike.strijkatelier.validation.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotEmpty;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@PasswordMatches
@ApiModel(value = "RegistrationRequest", description = "Request for user registration")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @MatchesPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotEmpty
    @ValidPassword
    private String password;

    private String matchingPassword;

    public RegistrationRequest(String firstName, String lastName, String email, String password, String matchingPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "String", example = "Lucas", notes = "First name of the user")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "Van den Abbeele", notes = "Last name of the user")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ApiModelProperty(position = 3, required = true, dataType = "String", example = "jon.doe@gmail.com", notes = "Email of the user")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty(position = 4, required = true, dataType = "String", example = "", notes = "Password of the user")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ApiModelProperty(position = 5, required = true, dataType = "String", example = "", notes = "Field to verify if password matches")
    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
