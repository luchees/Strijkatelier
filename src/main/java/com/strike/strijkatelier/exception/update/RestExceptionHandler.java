package com.strike.strijkatelier.exception.update;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @created 1/7/2020 AD
 * <p>
 * Controller Advice class that will catch API exceptions and map them to the corresponding ResponseEntity with the correct HttpStatus
 * <p>
 * Current mappings:
 * -   ResourceNotFoundException => 404
 * -   InputValidationException => 400
 * -   ResourceConflictException => 409
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorList> handleBadCredentialsException(final BadCredentialsException e) {
        log.error("BadCredentialsException occurred");
        return new ResponseEntity<>(new ErrorList(
                Collections.singletonList(
                        new ErrorInfo("Invalid email/password combination",
                                "Can not find user with given email & password",
                                "Please verify email & password"))), HttpStatus.UNAUTHORIZED);
    }
}
