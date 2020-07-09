package com.strike.strijkatelier.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(value = "CustomerDto", description = "CustomersDto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {


    private Long id;
    @NotEmpty
    @MatchesPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String emailAddress;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String accountNumber;
    private List<BasketDto> basketDtos;
    private double minutesLeft;
    private String note;

    @ApiModelProperty(position = 1, required = false, dataType = "number", example = "3", notes = "Id of the Customer")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "3", notes = "Email of the customer")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
     this.emailAddress=emailAddress;
    }
    @ApiModelProperty(position = 3, required = true, dataType = "String", example = "3", notes = "Firstname of the customer")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @ApiModelProperty(position = 4, required = true, dataType = "String", example = "3", notes = "Lastname of the customer")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @ApiModelProperty(position = 5, required = true, dataType = "String", example = "3", notes = "Phonenumber of the customer")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @ApiModelProperty(position = 6, required = false, dataType = "array", example = "3", notes = "Baskets of the customer")
    public List<BasketDto> getBasketDtos() {
        return basketDtos;
    }

    public void setBasketDtos(List<BasketDto> basketDtos) {
        this.basketDtos = basketDtos;
    }
    @ApiModelProperty(position = 7, required = true, dataType = "number", example = "3", notes = "MinutesLeft of the customer")
    public double getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(double minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    @ApiModelProperty(position = 8, required = false, dataType = "String", example = "3", notes = "Notes of the customer")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ApiModelProperty(position = 9, required = true, dataType = "String", example = "3", notes = "account Number of the customer")
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

