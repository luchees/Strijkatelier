package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {

    private String code;
    private String message;
    private String description;
    private String moreInfo;

    public ErrorInfo() {
    }

    public ErrorInfo(String code, String message, String description, String moreInfo) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.moreInfo = moreInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
