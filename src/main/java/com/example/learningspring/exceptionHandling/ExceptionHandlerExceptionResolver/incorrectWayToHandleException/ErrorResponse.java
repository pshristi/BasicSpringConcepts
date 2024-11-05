package com.example.learningspring.exceptionHandling.ExceptionHandlerExceptionResolver.incorrectWayToHandleException;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
public class ErrorResponse {
    LocalTime timestamp;
    String message;
    Integer status;
}
