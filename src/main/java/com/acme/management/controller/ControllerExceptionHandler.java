package com.acme.management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail notFound(Exception e) {
        logger.debug("Returning 404 with reason: {}", e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ProblemDetail badRequest(Exception e) {
        logger.debug("Returning 400 with reason: {}", e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ProblemDetail internalError(Exception e) {
        logger.debug("Returning 500 with reason: {}", e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
