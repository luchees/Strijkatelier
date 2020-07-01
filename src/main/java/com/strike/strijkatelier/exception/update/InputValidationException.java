package com.strike.strijkatelier.exception.update;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 * <p>
 * Exception used for validation errors in the API
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputValidationException extends GenericRuntimeException {

    private final ErrorList errorList;

    public InputValidationException(String code, String message, String description, String moreInfo) {
        super("Input Validation Exception with" +
                " code \"" + code + "\"" +
                " message \"" + message + "\"" +
                " description\"" + description + "\"" +
                " moreInfo \"" + moreInfo + "\"");
        ArrayList<ErrorInfo> inputErrorList = new ArrayList<ErrorInfo>();
        inputErrorList.add(new ErrorInfo(code, message, description, moreInfo));
        errorList = new ErrorList(inputErrorList);
    }

    public InputValidationException(List<ErrorInfo> errorList) {
        this.errorList = new ErrorList(errorList);
    }

    @Override
    public ErrorList getErrors() {
        return this.errorList;
    }
}
