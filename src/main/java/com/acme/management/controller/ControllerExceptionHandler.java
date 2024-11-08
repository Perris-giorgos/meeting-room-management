package com.acme.management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail notFound(Exception e) {
        logger.info("Returning 404 with reason: {}", e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ProblemDetail badRequest(Exception e) {
        logger.info("Returning 400 with reason: {}", e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
    }

}
