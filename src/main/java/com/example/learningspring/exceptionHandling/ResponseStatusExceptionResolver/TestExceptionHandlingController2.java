package com.example.learningspring.exceptionHandling.ResponseStatusExceptionResolver;

import com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException.CustomException;
import com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.time.LocalTime.now;


@RestController
public class TestExceptionHandlingController2 {

    @GetMapping("/user")
    public String getUser() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "An error occurred while fetching user data.");
    }
}
