# Layered Architecture in Spring

This package demonstrates the implementation of a layered architecture in Spring Framework, which is a common architectural pattern for organizing code in enterprise applications.

## Key Concepts Demonstrated

### Layered Architecture Overview
- **Separation of Concerns**: Each layer has a specific responsibility
- **Dependency Direction**: Dependencies flow downward, with higher layers depending on lower layers
- **Abstraction**: Each layer abstracts the details of the layers below it
- **Testability**: Layers can be tested independently

### Layers Implemented
The package demonstrates a typical four-layer architecture:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and orchestrates operations
- **Repository Layer**: Manages data access
- **Entity Layer**: Represents the domain model
- **DTO Layer**: Transfers data between layers

## Layer Responsibilities

### Controller Layer
The `controller` package demonstrates:
- Handling HTTP requests and mapping them to service methods
- Converting request parameters to DTOs
- Returning appropriate HTTP responses
- Input validation and data binding

```java
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable(value = "id") Long id,
                                                          @RequestBody User user) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setId(id);
        
        PaymentResponse paymentResponse = paymentService.getPaymentById(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
```

### Service Layer
The `service` package demonstrates:
- Implementing business logic
- Coordinating between controller and repository layers
- Converting between DTOs and domain entities
- Transaction management (implied but not explicitly shown)

```java
@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponse getPaymentById(PaymentRequest paymentRequest) {
        PaymentEntity paymentModel = paymentRepository.getPaymentById(paymentRequest);

        //Convert PaymentEntity to PaymentResponse
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentStatus(paymentModel.getPaymentStatus());
        return paymentResponse;
    }
}
```

### Repository Layer
The `repository` package demonstrates:
- Data access operations
- Returning domain entities
- Abstracting the data source (in this case, a mock implementation)

```java
@Repository
public class PaymentRepository {
    public PaymentEntity getPaymentById(PaymentRequest paymentRequest) {
        //Call to DB and return payment entity
        PaymentEntity paymentModel = new PaymentEntity();
        paymentModel.setId(paymentRequest.getId());
        paymentModel.setPaymentStatus("Done");
        return paymentModel;
    }
}
```

### Entity Layer
The `entity` package demonstrates:
- Domain model classes representing business entities
- Mapping between Java objects and database tables (implied)

```java
public class PaymentEntity {
    Long id;
    String paymentStatus;
    
    // Getters and setters
}
```

### DTO Layer
The `dto` package demonstrates:
- Data Transfer Objects for passing data between layers
- Separation between internal domain model and external API representation

```java
public class PaymentRequest {
    Long id;
    
    // Getters and setters
}

public class PaymentResponse {
    String paymentStatus;
    
    // Getters and setters
}
```

## Benefits of Layered Architecture

1. **Maintainability**: Changes in one layer don't affect other layers
2. **Testability**: Each layer can be tested independently
3. **Flexibility**: Implementations can be changed without affecting other layers
4. **Scalability**: Different layers can be scaled independently
5. **Separation of Concerns**: Each layer has a specific responsibility
6. **Team Organization**: Different teams can work on different layers
7. **Security**: Access control can be implemented at different layers

## Common Anti-Patterns to Avoid

1. **Layer Bypass**: Skipping layers (e.g., controller directly accessing repository)
2. **Fat Layers**: Putting too much logic in one layer
3. **Leaky Abstractions**: Exposing implementation details of one layer to another
4. **Anemic Domain Model**: Entities with no behavior, just getters and setters
5. **Tight Coupling**: Layers depending on specific implementations rather than abstractions
6. **Circular Dependencies**: Layers depending on each other
7. **Inconsistent Layer Boundaries**: Mixing responsibilities across layers

## Best Practices

1. **Use Dependency Injection**: Inject dependencies rather than creating them
2. **Follow Single Responsibility Principle**: Each class should have only one reason to change
3. **Use DTOs for Layer Boundaries**: Don't pass entities directly to the presentation layer
4. **Keep Controllers Thin**: Controllers should delegate to services for business logic
5. **Centralize Exception Handling**: Use global exception handlers
6. **Use Interfaces for Abstraction**: Define interfaces for services and repositories
7. **Implement Proper Validation**: Validate input at the controller layer
8. **Use Transactions Appropriately**: Define transaction boundaries at the service layer
9. **Follow Naming Conventions**: Use consistent naming across layers
10. **Document Layer Interactions**: Make it clear how layers interact

## Related Resources
- [Spring MVC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc)
- [Spring Data Access Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html)
- [Domain-Driven Design Concepts](https://martinfowler.com/tags/domain%20driven%20design.html)
- [Clean Architecture by Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Patterns of Enterprise Application Architecture by Martin Fowler](https://martinfowler.com/books/eaa.html)
