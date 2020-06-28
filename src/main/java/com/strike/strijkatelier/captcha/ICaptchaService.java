package com.strike.strijkatelier.captcha;

import com.strike.strijkatelier.web.error.*;

public interface ICaptchaService {

    default void processResponse(final String response) throws ReCaptchaInvalidException {
    }

    default void processResponse(final String response, String action) throws ReCaptchaInvalidException {
    }

    String getReCaptchaSite();

    String getReCaptchaSecret();
}
