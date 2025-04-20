package com.ecommerce.authservice.exception.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

@Slf4j
public class WarnExceptionLogger extends AbstractExceptionLogger {


    public WarnExceptionLogger(Exception exception, WebRequest webRequest) {
        super(exception, webRequest);
    }

    @Override
    public void logException() {
        if (log.isWarnEnabled()) {
            log.warn("{}: {}, \tRequest Description: {}",
                    exception.getClass().getSimpleName(),
                    exception.getMessage(),
                    webRequest.getDescription(false), exception);
        }
    }
}
