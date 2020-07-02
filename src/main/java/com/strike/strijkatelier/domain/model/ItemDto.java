package com.strike.strijkatelier.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;


@ApiModel(value = "ItemDto", description = "Request for Items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {

    private long id;
    @NotEmpty
    private String itemName;

    @DecimalMin("0.0")
    private double price;

    @DecimalMin("0")
    private int minutes;

    public ItemDto() {
    }

    @ApiModelProperty(position = 1, required = false, dataType = "int", example = "3", notes = "Id of the item")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "Rok", notes = "Name of the item")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @ApiModelProperty(position = 3, required = true, dataType = "Double", example = "3.50", notes = "Price of the item")

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ApiModelProperty(position = 4, required = true, dataType = "int", example = "10", notes = "Minutes of the item")
    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


}
