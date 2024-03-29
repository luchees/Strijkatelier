package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 *
 * Exception used when a resource was not found in the API
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceNotFoundException extends GenericRuntimeException{

    private ErrorList errorList;

    public ResourceNotFoundException(String message, String description, String moreInfo) {
        super("Input Validation Exception with" +
                " message \"" + message + "\"" +
                " description\"" + description + "\"" +
                " moreInfo \"" + moreInfo + "\"");
        ArrayList<ErrorInfo> inputErrorList = new ArrayList<ErrorInfo>();
        inputErrorList.add(new ErrorInfo(message, description, moreInfo));
        errorList = new ErrorList(inputErrorList);
    }


    @Override
    public ErrorList getErrors() {
        return this.errorList;
    }
}
