# Transaction Management in Spring

This package demonstrates different approaches to transaction management in Spring Framework, including declarative and programmatic transaction management, as well as transaction propagation behaviors.

## Key Concepts Demonstrated

### Transaction Architecture
- **Transaction Manager Hierarchy**: The class hierarchy of transaction managers in Spring
  - `TransactionManager` (interface)
  - `PlatformTransactionManager` (interface extending TransactionManager)
  - `AbstractPlatformTransactionManager` (abstract class)
  - Concrete implementations: `HibernateTransactionManager`, `DataSourceTransactionManager`, `JtaTransactionManager`, `JpaTransactionManager`, etc.

### Declarative Transaction Management
The `declarative` subpackage demonstrates:
- Using `@Transactional` annotation to define transaction boundaries
- Configuring transaction managers in Spring
- Specifying the transaction manager to use with `@Transactional(transactionManager = "userTransactionManager")`

### Programmatic Transaction Management
The `programmatic` subpackage demonstrates two approaches:

#### Approach 1: Using PlatformTransactionManager directly
- Manually starting a transaction with `getTransaction()`
- Explicitly committing or rolling back transactions
- Using try-catch blocks for exception handling

#### Approach 2: Using TransactionTemplate
- Creating a `TransactionCallback` to define operations within a transaction
- Using `execute()` method to run operations within a transaction
- Automatic commit/rollback handling

### Transaction Propagation
The `propagation` subpackage demonstrates:
- Different transaction propagation behaviors
- How transactions propagate from service layer to DAO layer
- Checking transaction status with `TransactionSynchronizationManager`
- Setting custom propagation behaviors:
  - `PROPAGATION_REQUIRED` (default): Use existing transaction or create a new one
  - `PROPAGATION_REQUIRES_NEW`: Always create a new transaction
  - Other propagation behaviors: `SUPPORTS`, `MANDATORY`, `NEVER`, `NOT_SUPPORTED`, `NESTED`

## Best Practices

1. **Use Declarative Transactions When Possible**: They're less verbose and less error-prone
2. **Be Aware of Proxy Limitations**: `@Transactional` only works for external method calls due to Spring's proxy-based implementation
3. **Set Appropriate Timeout Values**: Prevent long-running transactions from blocking resources
4. **Choose Appropriate Isolation Levels**: Based on your concurrency requirements
5. **Be Careful with Exception Handling**: Only runtime exceptions trigger rollback by default
6. **Use Read-Only Flag for Read Operations**: Allows database optimizations
7. **Be Mindful of Transaction Boundaries**: Keep transactions as short as possible

## Code Examples

### Declarative Transaction Management
```java
@Transactional(transactionManager = "userTransactionManager")
public void updateUser(String userId, String name) {
    // Database operations
}
```

### Programmatic Transaction Management (Approach 1)
```java
TransactionStatus status = userTransactionManager.getTransaction(null);
try {
    // Database operations
    userTransactionManager.commit(status);
} catch (Exception e) {
    userTransactionManager.rollback(status);
}
```

### Programmatic Transaction Management (Approach 2)
```java
TransactionCallback<TransactionStatus> doOperationTask = (TransactionStatus status) -> {
    // Database operations
    return status;
};
TransactionStatus status = transactionTemplate.execute(doOperationTask);
```

### Setting Propagation Behavior
```java
DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
transactionDefinition.setName("myTransaction");
transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
```

## Related Resources
- [Spring Transaction Management Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction)
- [Understanding Transaction Propagation](https://www.baeldung.com/spring-transactional-propagation-isolation)
