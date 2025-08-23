# Transaction Management in Spring

This package demonstrates different approaches to transaction management in Spring Framework, including declarative and programmatic transaction management, as well as transaction propagation behaviors.

## Key Concepts Demonstrated

### Transaction Architecture
- **Transaction Manager Hierarchy**: The class hierarchy of transaction managers in Spring
  - `TransactionManager` (interface)
  - `PlatformTransactionManager` (interface extending TransactionManager)
  - `AbstractPlatformTransactionManager` (abstract class)
  - Concrete implementations: `HibernateTransactionManager`, `DataSourceTransactionManager`, `JtaTransactionManager`, `JpaTransactionManager`, etc.

### Enabling Transaction Management
To enable transaction management in Spring, you need to:
1. Add the `@EnableTransactionManagement` annotation to a configuration class
2. Configure a transaction manager bean
3. Use transaction management approaches (declarative or programmatic)

When transaction management is enabled, Spring uses:
- `TransactionInterceptor` to intercept method calls with `@Transactional` annotation
- `TransactionAspectSupport.invokeWithinTransaction()` method to manage the transactional behavior

## Transaction Management Approaches

### Declarative Transaction Management
The `declarative` subpackage demonstrates using annotations to manage transactions.

#### Configuration
In `AppConfig.java`, we configure the necessary beans:
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource getDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

#### Implementation
In `UserDeclarative.java`, we use the `@Transactional` annotation:
```java
public class UserDeclarative {
    @Transactional(transactionManager = "userTransactionManager")
    public void updateUser(String userId, String name) {
        //Some DB Operation
        System.out.println("Updated user: " + userId + ", name: " + name);
    }
}
```

#### Code Flow
When the `updateUser` method is called:
1. Spring's transaction interceptor detects the `@Transactional` annotation
2. The transaction manager starts a new transaction
3. The method executes within the transaction context
4. If the method completes without exceptions, the transaction is committed
5. If an exception occurs, the transaction is rolled back (for runtime exceptions by default)

#### Output
```
Updated user: user123, name: John Doe
```
(Plus transaction commit/rollback messages in logs)

### Programmatic Transaction Management
The `programmatic` subpackage demonstrates two approaches to manually manage transactions.

#### Approach 1: Using PlatformTransactionManager directly
In `UserProgrammaticApproach1.java`:
```java
public class UserProgrammaticApproach1 {
    @Autowired
    PlatformTransactionManager userTransactionManager;

    public void updateUser(String userId, String name) {
        TransactionStatus status = userTransactionManager.getTransaction(null);
        try{
            //Some DB Operation
            System.out.println("Updated user: " + userId + ", name: " + name);
            userTransactionManager.commit(status);
        }
        catch (Exception e) {
            userTransactionManager.rollback(status);
        }
    }
}
```

#### Code Flow (Approach 1)
When the `updateUser` method is called:
1. A transaction is manually started using `getTransaction()`
2. Database operations are performed within a try block
3. If operations complete successfully, the transaction is committed with `commit()`
4. If an exception occurs, the transaction is rolled back with `rollback()`

#### Output (Approach 1)
```
Updated user: user123, name: John Doe
```
(Plus transaction commit message in logs)

#### Approach 2: Using TransactionTemplate
In `UserProgrammaticApproach2.java`:
```java
public class UserProgrammaticApproach2 {
    @Autowired
    TransactionTemplate transactionTemplate;

    public void updateUser(String userId, String name) {
        TransactionCallback<TransactionStatus> doOperationTask = (TransactionStatus status) -> {
            //Some DB Operation
            System.out.println("Updated user: " + userId + ", name: " + name);
            return status;
        };
        TransactionStatus status = transactionTemplate.execute(doOperationTask);
    }
}
```

#### Code Flow (Approach 2)
When the `updateUser` method is called:
1. A `TransactionCallback` is created to define operations within the transaction
2. The `execute()` method of `TransactionTemplate` is called with the callback
3. The transaction template:
   - Starts a transaction
   - Executes the callback within the transaction
   - Commits the transaction if the callback completes without exceptions
   - Rolls back the transaction if an exception occurs

#### Output (Approach 2)
```
Updated user: user123, name: John Doe
```
(Plus transaction commit message in logs)

### Transaction Propagation
The `propagation` subpackage demonstrates how transactions propagate between different layers of an application.

#### Service Layer
In `UserServiceTransactionPropagationTest.java`:
```java
@Component
public class UserServiceTransactionPropagationTest {
    @Autowired
    UserDaoTransactionPropagationTest userDaoTransactionPropagationTest;

    @Transactional
    public void method1() {
        System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
        //Do some database operations
        userDaoTransactionPropagationTest.method2();
        //Do more database operations
    }
}
```

#### DAO Layer
In `UserDaoTransactionPropagationTest.java`:
```java
@Repository
public class UserDaoTransactionPropagationTest {
    @Transactional
    public void method2() {
        System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
        //Do some database operations
    }

    //Programmatic transaction management with REQUIRES_NEW
    public void method3ProgrammaticApproach1() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setName("myTransaction");
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = userTransactionManager.getTransaction(transactionDefinition);
        try {
            System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
            System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
            //Do some database operations
            userTransactionManager.commit(status);
        }
        catch (Exception e) {
            userTransactionManager.rollback(status);
        }
    }
}
```

#### Code Flow (Default Propagation - REQUIRED)
When `method1()` calls `method2()`:
1. `method1()` starts a transaction (let's call it "Transaction A")
2. When `method2()` is called, it detects an existing transaction
3. Due to default `PROPAGATION_REQUIRED` behavior, `method2()` joins "Transaction A"
4. Both methods execute within the same transaction
5. If either method throws an exception, the entire transaction is rolled back

#### Output (Default Propagation)
```
Current transaction active : true
Current transaction name : com.example.learningspring.transactions.propagation.UserServiceTransactionPropagationTest.method1
Current transaction active : true
Current transaction name : com.example.learningspring.transactions.propagation.UserServiceTransactionPropagationTest.method1
```
Note that both methods show the same transaction name, indicating they're using the same transaction.

#### Code Flow (REQUIRES_NEW Propagation)
When `method1()` calls `method3ProgrammaticApproach1()`:
1. `method1()` starts a transaction (let's call it "Transaction A")
2. When `method3ProgrammaticApproach1()` is called, it creates a new transaction definition with `PROPAGATION_REQUIRES_NEW`
3. This causes the existing transaction to be suspended and a new transaction to be created (let's call it "Transaction B")
4. `method3ProgrammaticApproach1()` executes within "Transaction B"
5. When `method3ProgrammaticApproach1()` completes, "Transaction B" is committed or rolled back
6. "Transaction A" is resumed and continues execution
7. When `method1()` completes, "Transaction A" is committed or rolled back

#### Output (REQUIRES_NEW Propagation)
```
Current transaction active : true
Current transaction name : com.example.learningspring.transactions.propagation.UserServiceTransactionPropagationTest.method1
Current transaction active : true
Current transaction name : myTransaction
```
Note that the second method shows a different transaction name ("myTransaction"), indicating it's using a separate transaction.

## Transaction Propagation Behaviors

Spring supports several propagation behaviors:

1. **PROPAGATION_REQUIRED** (default): Use existing transaction or create a new one
   - If a transaction exists: Join it
   - If no transaction exists: Create a new one

2. **PROPAGATION_REQUIRES_NEW**: Always create a new transaction
   - If a transaction exists: Suspend it and create a new one
   - If no transaction exists: Create a new one

3. **PROPAGATION_SUPPORTS**: Support a transaction if one exists
   - If a transaction exists: Join it
   - If no transaction exists: Execute non-transactionally

4. **PROPAGATION_MANDATORY**: Require an existing transaction
   - If a transaction exists: Join it
   - If no transaction exists: Throw an exception

5. **PROPAGATION_NEVER**: Execute non-transactionally, throw an exception if a transaction exists
   - If a transaction exists: Throw an exception
   - If no transaction exists: Execute non-transactionally

6. **PROPAGATION_NOT_SUPPORTED**: Execute non-transactionally, suspend any existing transaction
   - If a transaction exists: Suspend it and execute non-transactionally
   - If no transaction exists: Execute non-transactionally

7. **PROPAGATION_NESTED**: Execute within a nested transaction if a transaction exists
   - If a transaction exists: Create a savepoint and execute within a nested transaction
   - If no transaction exists: Behave like PROPAGATION_REQUIRED

## Best Practices

1. **Use Declarative Transactions When Possible**: They're less verbose and less error-prone
2. **Be Aware of Proxy Limitations**: `@Transactional` only works for external method calls due to Spring's proxy-based implementation
3. **Set Appropriate Timeout Values**: Prevent long-running transactions from blocking resources
4. **Choose Appropriate Isolation Levels**: Based on your concurrency requirements
5. **Be Careful with Exception Handling**: Only runtime exceptions trigger rollback by default
6. **Use Read-Only Flag for Read Operations**: Allows database optimizations
7. **Be Mindful of Transaction Boundaries**: Keep transactions as short as possible

## Related Resources
- [Spring Transaction Management Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction)
- [Understanding Transaction Propagation](https://www.baeldung.com/spring-transactional-propagation-isolation)
