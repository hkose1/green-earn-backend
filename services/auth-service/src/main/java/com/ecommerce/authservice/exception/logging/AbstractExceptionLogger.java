package com.ecommerce.authservice.exception.logging;


import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
public abstract class AbstractExceptionLogger implements ExceptionLogger {

    protected final Exception exception;
    protected final WebRequest webRequest;

}
