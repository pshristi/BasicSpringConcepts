package com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.globalExceptionHandler;

import com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException.CustomException;
import com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalTime.now;

@ControllerAdvice
public class GlobalExceptionHandling {
    //If handler is not found in specific controllers, then it will be looked up in global exception handler
    @ExceptionHandler({CustomException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
