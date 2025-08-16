package com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.correctWayToHandlePerControllerException;

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
public class TestExceptionHandlingController1 {

    @GetMapping("v2/user")
    public String getUserV2() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "An error occurred while fetching user data.");
    }

    @GetMapping("/user-history")
    public String getUserV1() {
        throw new IllegalArgumentException("An error occurred while fetching user data.");
    }

    @GetMapping("user1")
    public String getUser1() {
        throw new NullPointerException("An error occurred while fetching user data.");
    }

    //If I am creating ResponseEntity with appropriate status and message, then Exception Resolver will not call DefaultErrorAttributes to create ResponseEntity
    @ExceptionHandler({CustomException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    //When I am not setting ResponseEntity, then DefaultErrorAttributes will handle it.
    @ExceptionHandler(NullPointerException.class)
    public void handleCustomExceptions1(HttpServletResponse response, NullPointerException ex) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}

//My ExceptionHandlerExceptionResolver will handle 2 annotations - @ControllerAdvice and @ExceptionHandler
