package com.strike.strijkatelier.exception.update;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */
public abstract class GenericRuntimeException extends Exception {

    public GenericRuntimeException(){

    }

    public GenericRuntimeException(String message)
    {
        super(message);
    }

    public GenericRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public abstract ErrorList getErrors();

}
