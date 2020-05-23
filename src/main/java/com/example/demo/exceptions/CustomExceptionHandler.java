package com.example.demo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    public CustomExceptionHandler() {
        super();
    }

    @ExceptionHandler({InvalidParameterException.class})
    public ResponseEntity<Object> handleBadRequest(final InvalidParameterException ex, final WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        InvalidParameterException ipe = (InvalidParameterException) ex;
        return handleExceptionInternal(ipe, ipe.getMessage(), headers, status, request);
    }
}
