package com.strike.strijkatelier.validation;

import com.strike.strijkatelier.domain.model.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RegistrationRequest request  = (RegistrationRequest) obj;
        return request.getPassword().equals(request.getMatchingPassword());
    }

}
