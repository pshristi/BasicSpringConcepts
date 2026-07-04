# ResponseEntity in Spring

This package demonstrates how to use ResponseEntity in Spring Framework to build HTTP responses with fine-grained control over status codes, headers, and body content.

## Key Concepts Demonstrated

### ResponseEntity Overview
- **Complete HTTP Response Control**: Customize status codes, headers, and body content
- **Type Safety**: Generic type parameter for the response body
- **Builder Pattern**: Fluent API for constructing responses
- **Status Code Management**: Using predefined HttpStatus enums

### HTTP Status Codes
HTTP response status codes indicate whether a specific HTTP request has been successfully completed. Responses are grouped into five classes: informational (100–199), successful (200–299), redirection (300–399), client error (400–499), and server error (500–599).
The package includes visual references for different HTTP status code categories:
- **1xx (Informational)**: Request received, continuing process
- **2xx (Success)**: Request successfully received, understood, and accepted
- **3xx (Redirection)**: Further action needed to complete the request
- **4xx (Client Error)**: Request contains bad syntax or cannot be fulfilled
- **5xx (Server Error)**: Server failed to fulfill a valid request

#### Informational Responses (1xx)
- **100 Continue**: This interim response indicates that the client should continue the request or ignore the response if the request is already finished.
- **102 Processing**: This code indicates that the server has received and is processing the request, but no response is available yet.

#### Successful Responses (2xx)
- **200 OK**: The request succeeded. The exact meaning of "success" depends on the HTTP method.
  - **GET**: The resource has been fetched and transmitted in the message body.
  - **HEAD**: The representation headers are included in the response without any message body.
  - **PUT or POST**: The resource describing the result of the action is transmitted in the message body.
  - **TRACE**: The message body contains the request message as received by the server.
- **201 Created**: The request succeeded, and a new resource was created as a result. This is typically the response sent after POST requests, or some PUT requests.
- **202 Accepted**: The request has been received but not yet acted upon. It is noncommittal, since there is no way in HTTP to later send an asynchronous response indicating the outcome of the request. It is intended for cases where another process or server handles the request, or for batch processing.

#### Client Error Responses (4xx)
- **400 Bad Request**: The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).
- **401 Unauthorized**: Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated". That is, the client must authenticate itself to get the requested response.
- **403 Forbidden**: The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource. Unlike 401 Unauthorized, the client's identity is known to the server.
- **404 Not Found**: The server cannot find the requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of 403 Forbidden to hide the existence of a resource from an unauthorized client. This response code is probably the most well known due to its frequent occurrence on the web.

#### Server Error Responses (5xx)
- **500 Internal Server Error**: The server has encountered a situation it does not know how to handle.
- **502 Bad Gateway**: This error response means that the server, while working as a gateway to get a response needed to handle the request, got an invalid response.
- **503 Service Unavailable**: The server is not ready to handle the request. Common causes are a server that is down for maintenance or that is overloaded. A user-friendly page explaining the problem should be sent along with this response. This response should be used for temporary conditions, and the Retry-After HTTP header should, if possible, contain the estimated time before the recovery of the service. Caching-related headers sent along with this response should usually not allow it to be cached.
- **504 Gateway Timeout**: This error response is given when the server is acting as a gateway and cannot get a response in time.

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
