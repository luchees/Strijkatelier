package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@ApiModel(value = "ErrorInfo", description = "Error detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {

    private String message;
    private String description;
    private String moreInfo;

    public ErrorInfo() {
    }

    public ErrorInfo(String message, String description, String moreInfo) {
        this.message = message;
        this.description = description;
        this.moreInfo = moreInfo;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "String", example = "Missing field userName", notes = "Error Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ApiModelProperty(position = 2, required = true, dataType = "String", example = "userName field is required for registering a new user", notes = "Description of the problem")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ApiModelProperty(position = 3, required = false, dataType = "String", example = "Please make sure that the requestbody contains the field userName", notes = "More information on the problem and or recommendations to resolve the error")
    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
