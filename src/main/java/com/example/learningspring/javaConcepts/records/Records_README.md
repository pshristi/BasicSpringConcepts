# Java Records

## Overview
Java Records were introduced as a preview feature in Java 14 and became a standard feature in Java 16. Records provide a compact syntax for declaring classes that are transparent holders for shallowly immutable data.

## Key Features and Benefits
- **Concise syntax**: Dramatically reduces boilerplate code for data carrier classes
- **Immutability**: All fields are final by default, promoting immutable design
- **Built-in methods**: Automatic generation of equals(), hashCode(), toString(), and accessor methods
- **Constructor generation**: Canonical constructor is automatically generated
- **Deconstruction patterns**: Support for pattern matching (Java 16+)
- **Transparent**: Records clearly communicate their purpose as data carriers

## Example from Code
```java
public record RateCardLineItemPortingMessage(Integer entityId, Integer entityTypeId,
                                           Integer sourceEntityId,
                                           Integer sourceEntityTypeId,
                                           Integer clientId,
                                           String user,
                                           Integer userId,
                                           boolean generateRU) {}
```

In this example:
- A record is declared with 8 components (fields)
- No explicit methods are needed for basic functionality
- The record is immutable - all fields are implicitly final
- Accessor methods are automatically generated with the same name as the components

## Comparison with Traditional Class
### Traditional Class:
```java
public class RateCardLineItemPortingMessage {
    private final Integer entityId;
    private final Integer entityTypeId;
    private final Integer sourceEntityId;
    private final Integer sourceEntityTypeId;
    private final Integer clientId;
    private final String user;
    private final Integer userId;
    private final boolean generateRU;

    public RateCardLineItemPortingMessage(Integer entityId, Integer entityTypeId,
                                        Integer sourceEntityId, Integer sourceEntityTypeId,
                                        Integer clientId, String user, Integer userId,
                                        boolean generateRU) {
        this.entityId = entityId;
        this.entityTypeId = entityTypeId;
        this.sourceEntityId = sourceEntityId;
        this.sourceEntityTypeId = sourceEntityTypeId;
        this.clientId = clientId;
        this.user = user;
        this.userId = userId;
        this.generateRU = generateRU;
    }

    // Getters
    public Integer getEntityId() { return entityId; }
    public Integer getEntityTypeId() { return entityTypeId; }
    public Integer getSourceEntityId() { return sourceEntityId; }
    public Integer getSourceEntityTypeId() { return sourceEntityTypeId; }
    public Integer getClientId() { return clientId; }
    public String getUser() { return user; }
    public Integer getUserId() { return userId; }
    public boolean isGenerateRU() { return generateRU; }

    // equals, hashCode, and toString methods...
}
```

### Record:
```java
public record RateCardLineItemPortingMessage(Integer entityId, Integer entityTypeId,
                                           Integer sourceEntityId,
                                           Integer sourceEntityTypeId,
                                           Integer clientId,
                                           String user,
                                           Integer userId,
                                           boolean generateRU) {}
```

## Usage Scenarios
- Data Transfer Objects (DTOs)
- Value objects in Domain-Driven Design
- API responses and requests
- Configuration objects
- Any immutable data carrier class

## Additional Information
- Records can have static fields, methods, and initializers
- Records can implement interfaces
- Records cannot extend other classes
- Records cannot be extended by other classes
- Records cannot have instance fields other than the components in the header
- Records can have custom accessors, constructors, and methods
- Records can be nested and can be generic
