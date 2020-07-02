package com.strike.strijkatelier.domain.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "BasketDto", description = "Request for Basket")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasketDto {

    public boolean active;
    @NotEmpty
    public LocalDateTime startDateTime;
    public LocalDateTime doneDateTime;
    @NotEmpty
    public List<ItemDto> itemDtos;
    public double price;
    public boolean cash;
    @Id
    private Long id;
    @NotEmpty
    private CustomerDto customerDto;

    @ApiModelProperty(position = 1, required = false, dataType = "number", example = "3", notes = "Id of the Basket")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "object", example = "{customerDto}", notes = "Customer of the basket")
    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "boolean", example = "true", notes = "Is basket active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ApiModelProperty(position = 3, required = true, dataType = "LocalDateTime", example = "2/07/2020T18:57:20", notes = "Startdate of the Basket")
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @ApiModelProperty(position = 4, required = true, dataType = "LocalDateTime", example = "2/07/2020T18:57:20", notes = "Date when Basket is done")
    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }

    @ApiModelProperty(position = 5, required = true, dataType = "object", example = "{items}", notes = "items of the Basket")
    public List<ItemDto> getItemDtos() {
        return itemDtos;
    }

    public void setItemDtos(List<ItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }

    @ApiModelProperty(position = 6, required = false, dataType = "number", example = "20", notes = "Startdate of the Basket")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ApiModelProperty(position = 7, required = true, dataType = "boolean", example = "false", notes = "payment method of Basket")
    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    @ApiModelProperty(position = 8, required = true, dataType = "array", example = "false", notes = "add items to Basket")
    public void addItemDtos(List<ItemDto> itemDtos) {
        this.itemDtos.addAll(itemDtos);
    }
}
