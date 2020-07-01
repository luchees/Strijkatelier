package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 *
 *
 */

@ApiModel(value = "Errors", description = "List of Errors that occurred")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorList {

    private List<ErrorInfo> errors;

    public ErrorList() {
    }

    public ErrorList(List<ErrorInfo> errors) {
        this.errors = errors;
    }

    @ApiModelProperty(position = 1, required = true, dataType = "List<ErrorInfo>", notes = "List of ErrorInfo")
    public List<ErrorInfo> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }
}
