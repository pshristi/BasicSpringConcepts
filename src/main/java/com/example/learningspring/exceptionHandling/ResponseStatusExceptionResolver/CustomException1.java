package com.example.learningspring.exceptionHandling.ResponseStatusExceptionResolver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred while processing the request." )
public class CustomException1 extends RuntimeException {

    HttpStatus statusCode;
    String message;

    public CustomException1(HttpStatus statusCode, String message) {
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
