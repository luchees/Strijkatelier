package com.strike.strijkatelier.domain.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@ApiModel(value = "BasketDto", description = "Request for Basket")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceCheckDto {

    @Id
    private Long id;
    @NotEmpty
    private String serviceCheckNumber;
    @NotEmpty
    @Basic
    private LocalDate usedDate;
    @NotEmpty
    @Basic
    private LocalDate expiryDate;
    @NotEmpty
    private boolean signed;

    @NotEmpty
    private BasketDto basketDto;

    @ApiModelProperty(position = 1, required = true, dataType = "number", example = "3", notes = "Id of the service check")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "78 124687486 154684", notes = "Number of the service check")
    public String getServiceCheckNumber() {
        return serviceCheckNumber;
    }

    public void setServiceCheckNumber(String serviceCheckNumber) {
        this.serviceCheckNumber = serviceCheckNumber;
    }
    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "12/05/2021", notes = "Used date of the check")
    public LocalDate getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDate usedDate) {
        this.usedDate = usedDate;
    }
    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "12/05/2021", notes = "Expiry date of the check")
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    @ApiModelProperty(position = 2, required = true, dataType = "boolean", example = "true", notes = "is check signed")
    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "object", example = "{BasketDto}", notes = "basket of the check")
    public BasketDto getBasketDto() {
        return basketDto;
    }

    public void setBasketDto(BasketDto basketDto) {
        this.basketDto = basketDto;
    }
}
