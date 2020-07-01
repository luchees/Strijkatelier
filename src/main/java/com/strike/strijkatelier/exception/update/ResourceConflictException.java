package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 *
 * Exception used for Business rule violations in the API
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceConflictException extends GenericRuntimeException {

    private ErrorList errorList;

    public ResourceConflictException(String code, String message, String description, String moreInfo) {
        super("Resource Conflict Exception with" +
                " code \""+code +"\"" +
                " message \""+message+"\"" +
                " description\""+description+"\"" +
                " moreInfo \""+moreInfo+"\"");
        ArrayList<ErrorInfo> inputErrorList = new ArrayList<ErrorInfo>();
        inputErrorList.add(new ErrorInfo(code,message,description,moreInfo));
        errorList = new ErrorList(inputErrorList);

    }

    @Override
    public ErrorList getErrors() {
        return this.getErrors();
    }
}
