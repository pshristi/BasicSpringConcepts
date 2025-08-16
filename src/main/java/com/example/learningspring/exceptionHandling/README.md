# Exception Handling in Spring

This package demonstrates different approaches to exception handling in Spring Framework, including controller-specific exception handling, global exception handling, and using @ResponseStatus annotation.

## Key Concepts Demonstrated

### Exception Handling Mechanisms
The package demonstrates two main exception resolvers in Spring:
- **ExceptionHandlerExceptionResolver**: Handles exceptions using @ExceptionHandler methods in controllers or @ControllerAdvice classes
- **ResponseStatusExceptionResolver**: Handles exceptions annotated with @ResponseStatus

### Controller-Specific Exception Handling
The `correctWayToHandlePerControllerException` subpackage demonstrates:
- Using `@ExceptionHandler` annotation within a controller to handle specific exceptions
- Different ways to return error responses:
  - Returning ResponseEntity with custom error details
  - Using HttpServletResponse to send error status and message

```java
@RestController
public class TestExceptionHandlingController1 {
    @GetMapping("v2/user")
    public String getUserV2() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "An error occurred while fetching user data.");
    }
    
    @ExceptionHandler({CustomException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
```

### Global Exception Handling
The `globalExceptionHandler` subpackage demonstrates:
- Using `@ControllerAdvice` to create a global exception handler
- Handling exceptions across multiple controllers
- Providing consistent error responses throughout the application

```java
@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler({CustomException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
```

### @ResponseStatus Annotation
The `ResponseStatusExceptionResolver` subpackage demonstrates:
- Annotating exception classes with `@ResponseStatus` to automatically map them to HTTP status codes
- Simplifying exception handling by letting Spring automatically handle the response

```java
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred while processing the request.")
public class CustomException1 extends RuntimeException {
    // Exception implementation
}
```

### Common Anti-Patterns
The `incorrectWayToHandleException` subpackage demonstrates:
- Anti-patterns in exception handling, such as:
  - Catching exceptions in controller methods with try-catch blocks
  - Manually creating error responses in each controller method
  - Inconsistent error response formats

## Exception Handling Flow in Spring

1. When an exception is thrown in a controller method, Spring's DispatcherServlet delegates to its configured exception resolvers
2. Spring tries to find an `@ExceptionHandler` method in the same controller
3. If not found, it looks for a matching handler in any `@ControllerAdvice` classes
4. If the exception is annotated with `@ResponseStatus`, ResponseStatusExceptionResolver handles it
5. If no handler is found, the exception propagates to the container

## Best Practices

1. **Use @ControllerAdvice for Global Exception Handling**: Create a centralized exception handling component
2. **Create Custom Exception Classes**: Define domain-specific exceptions that extend RuntimeException
3. **Use @ResponseStatus for Simple Cases**: For straightforward mappings between exceptions and HTTP status codes
4. **Return Consistent Error Responses**: Define a standard error response format
5. **Include Relevant Information**: Error responses should include timestamp, message, status code, and potentially a path
6. **Log Exceptions Appropriately**: Log exceptions with appropriate severity levels
7. **Handle Different Exception Types Differently**: Map different exceptions to appropriate HTTP status codes
8. **Don't Expose Sensitive Information**: Sanitize error messages before returning them to clients
9. **Consider Internationalization**: Support localized error messages
10. **Test Exception Handling**: Write tests specifically for exception scenarios

## Common Use Cases

1. **Validation Errors**: Handling invalid input data
2. **Resource Not Found**: Handling requests for non-existent resources
3. **Authorization Failures**: Handling unauthorized access attempts
4. **Business Rule Violations**: Handling violations of business rules
5. **External Service Failures**: Handling failures in external service calls
6. **Data Integrity Issues**: Handling database constraints or integrity violations

## Related Resources
- [Spring Exception Handling Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-exceptionhandler)
- [Guide to @ExceptionHandler in Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Spring Boot Error Handling](https://www.baeldung.com/spring-boot-error-handling)
