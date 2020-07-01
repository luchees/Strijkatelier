package com.strike.strijkatelier.exception.update;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 *
 * Controller Advice class that will catch API exceptions and map them to the corresponding ResponseEntity with the correct HttpStatus
 *
 * Current mappings:
 *  -   ResourceNotFoundException => 404
 *  -   InputValidationException => 400
 *  -   ResourceConflictException => 409
 */

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorList> handleResourceNotFoundException(final ResourceNotFoundException e) {
        log.error("ResourceNotFoundException occurred");
        return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorList> handleResourceConflictException(final ResourceConflictException e) {
        log.error("ResourceConflictException occurred");
        return new ResponseEntity<>(e.getErrors(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<ErrorList> handleInputValidationException(final InputValidationException e) {
        log.error("ResourceConflictException occurred");
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }

}
