package com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalTime.now;

@RestController
public class TestExceptionHandlingController {

    @GetMapping("/user")
    public String getUser() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "An error occurred while fetching user data.");
    }

    @GetMapping("/v1/user")
    public ResponseEntity<?> getUserV1() {
        try {
            throw new CustomException(HttpStatus.BAD_REQUEST, "An error occurred while fetching user data.");
        }
        catch (CustomException e) {
            ErrorResponse errorResponse = new ErrorResponse(now(), e.getMessage(), e.getStatusCode().value());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }
}
//If we directly return exception, then Exception Resolver will create ResponseEntity with appropriate status and message
//If we want full control over response, then we should use ResponseEntity with appropriate status and message.
