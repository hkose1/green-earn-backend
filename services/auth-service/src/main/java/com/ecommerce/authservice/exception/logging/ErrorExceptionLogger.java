package com.ecommerce.authservice.exception.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

@Slf4j
public class ErrorExceptionLogger extends AbstractExceptionLogger {

    public ErrorExceptionLogger(Exception exception, WebRequest webRequest) {
        super(exception, webRequest);
    }

    @Override
    public void logException() {
        if (log.isErrorEnabled()) {
            log.error("{}: {}, \tRequest Description: {}",
                    exception.getClass().getSimpleName(),
                    exception.getMessage(),
                    webRequest.getDescription(false), exception);
        }
    }
}
