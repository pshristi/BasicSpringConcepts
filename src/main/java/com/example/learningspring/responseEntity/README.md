# ResponseEntity in Spring

This package demonstrates how to use ResponseEntity in Spring Framework to build HTTP responses with fine-grained control over status codes, headers, and body content.

## Key Concepts Demonstrated

### ResponseEntity Overview
- **Complete HTTP Response Control**: Customize status codes, headers, and body content
- **Type Safety**: Generic type parameter for the response body
- **Builder Pattern**: Fluent API for constructing responses
- **Status Code Management**: Using predefined HttpStatus enums

### HTTP Status Codes
The package includes visual references for different HTTP status code categories:
- **1xx (Informational)**: Request received, continuing process
- **2xx (Success)**: Request successfully received, understood, and accepted
- **3xx (Redirection)**: Further action needed to complete the request
- **4xx (Client Error)**: Request contains bad syntax or cannot be fulfilled
- **5xx (Server Error)**: Server failed to fulfill a valid request

## ResponseEntity Implementation

### Basic Response with Status and Body
The `TestResponseEntityController` demonstrates creating a basic response with status code and body:

```java
@GetMapping("/user")
public ResponseEntity<String> getUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Key1", "Value1");
    return ResponseEntity.status(HttpStatus.OK)
            .headers(headers)
            .body("User Data");
}
```

### Response with Headers but No Body
For operations that don't return content (like some POST operations):

```java
@PostMapping("/user")
public ResponseEntity<Void> setUser(String user) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Key2", "Value2");
    return ResponseEntity.status(HttpStatus.OK)
            .headers(headers)
            .build();
}
```

### Implementing Redirects
The `Using301ErrorCode` class demonstrates how to implement HTTP redirects:

```java
@GetMapping("/old-get-user")
public ResponseEntity<String> getUserOld() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/new-get-user");
    return ResponseEntity.status(301).headers(headers).body("Redirecting to new endpoint /new-get-user");
}
```

### Direct Response vs. ResponseEntity
The package also demonstrates the difference between returning a direct response and using ResponseEntity:

```java
@GetMapping("/v1/user")
@ResponseBody
public String getUserV1() {
    return "User Data";
}
```

## Common Use Cases for ResponseEntity

1. **REST API Development**: Providing detailed control over HTTP responses
2. **Custom Error Responses**: Returning structured error information
3. **File Downloads**: Setting content-disposition and other headers
4. **Caching Control**: Setting cache-related headers
5. **Redirects**: Implementing URL redirects with appropriate status codes
6. **Conditional Responses**: Returning different status codes based on conditions
7. **Authentication Responses**: Returning appropriate status codes for auth scenarios
8. **Pagination**: Including pagination information in headers

## ResponseEntity Builder Methods

ResponseEntity provides several convenient static methods for common scenarios:

- `ResponseEntity.ok()`: Returns 200 OK status
- `ResponseEntity.created(URI)`: Returns 201 Created status with Location header
- `ResponseEntity.accepted()`: Returns 202 Accepted status
- `ResponseEntity.noContent()`: Returns 204 No Content status
- `ResponseEntity.badRequest()`: Returns 400 Bad Request status
- `ResponseEntity.notFound()`: Returns 404 Not Found status
- `ResponseEntity.status(HttpStatus)`: Returns the specified status

## Best Practices

1. **Use Appropriate Status Codes**: Choose the most specific HTTP status code for each situation
2. **Include Meaningful Headers**: Add relevant headers to provide context
3. **Structure Error Responses**: Use a consistent format for error responses
4. **Use Type Parameters**: Specify the response body type for type safety
5. **Consider Response Entity Design**: Design response entities that provide useful information
6. **Use Builder Methods**: Utilize the static builder methods for common scenarios
7. **Document API Responses**: Document all possible response status codes and formats
8. **Handle Exceptions Properly**: Map exceptions to appropriate ResponseEntity responses
9. **Test Different Response Scenarios**: Ensure all status codes and headers work as expected
10. **Be Consistent**: Use a consistent approach to ResponseEntity across your application

## Related Resources
- [Spring ResponseEntity Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- [HTTP Status Codes Reference](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [Spring REST Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-responseentity)
- [Building RESTful Services with Spring](https://spring.io/guides/tutorials/rest/)
- [Best Practices for RESTful APIs](https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design)
