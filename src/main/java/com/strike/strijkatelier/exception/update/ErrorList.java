package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorList {

    private List<ErrorInfo> errors;

    public ErrorList() {
    }

    public ErrorList(List<ErrorInfo> errors) {
        this.errors = errors;
    }

    public List<ErrorInfo> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }
}
