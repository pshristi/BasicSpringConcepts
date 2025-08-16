package com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    HttpStatus statusCode;
    String message;

    public CustomException(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
