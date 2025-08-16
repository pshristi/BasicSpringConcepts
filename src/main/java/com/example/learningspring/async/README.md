# Asynchronous Processing in Spring

This package demonstrates various approaches to asynchronous processing in Spring Framework, which allows methods to be executed in separate threads, improving application responsiveness and scalability.

## Key Concepts Demonstrated

### Asynchronous Method Execution
- **@Async Annotation**: Making methods run asynchronously in separate threads
- **@EnableAsync**: Required configuration to enable asynchronous processing
- **Thread Pool Configuration**: Different ways to configure thread pools for async execution

### Thread Pool Task Executors
The package demonstrates three approaches to configuring thread pools:

#### Default Thread Pool (defaultThreadPoolTaskExecutor)
- Using Spring's default SimpleAsyncTaskExecutor
- How Spring selects the default executor
- Basic @Async usage without explicit executor configuration

#### Custom Thread Pool (customThreadPoolTaskExecutor)
- Creating a custom ThreadPoolTaskExecutor bean
- Configuring core and max pool sizes, queue capacity, and thread naming
- Specifying which executor to use with @Async("executorName")

#### Best Practices for Configuration (bestPracticeForConfig)
- Implementing AsyncConfigurer for more control over async execution
- Setting up thread pools with proper rejection policies
- Thread-safe executor initialization

### Exception Handling in Async Methods
The `exceptionHandling` subpackage demonstrates:
- Creating a custom AsyncUncaughtExceptionHandler
- Configuring the exception handler through AsyncConfigurer
- Different approaches to exception handling based on return type:
  - For void methods: Using AsyncUncaughtExceptionHandler
  - For methods with return values: Catching exceptions when calling Future.get()

### Return Values with Future
The `returnValueUsingFuture` subpackage demonstrates:
- Returning Future objects from @Async methods
- Using AsyncResult to wrap return values
- Waiting for async method completion with Future.get()
- Handling exceptions from async methods

### Transactions in Async Methods
The `asyncWithTransaction.java` file notes an important limitation:
- Transaction context doesn't get transferred to async threads from the caller thread
- This means @Transactional doesn't work as expected with @Async methods

## Code Examples

### Basic @Async Usage
```java
@Service
public class AsyncTestService {
    @Async
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }
}
```

### Custom Thread Pool Configuration
```java
@Configuration
public class AppConfig {
    @Bean
    public Executor customTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(3);
        executor.setThreadNamePrefix("Custom-Thread-");
        executor.initialize();
        return executor;
    }
}
```

### Using a Specific Executor
```java
@Service
public class AsyncTestService1 {
    @Async("customTaskExecutor")
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }
}
```

### Returning Values from Async Methods
```java
@Service
public class AsyncTestService3 {
    @Async
    public Future<String> getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
        return new AsyncResult<>("Async data returned");
    }
}
```

### Handling Async Method Results
```java
@RestController
public class AsyncTestController3 {
    @Autowired
    AsyncTestService3 asyncTestService3;

    @GetMapping("/getUser")
    public String getUser() {
        Future<String> result = asyncTestService3.getAsyncData();
        try {
            return result.get(); // Blocks until the result is available
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting async data";
        }
    }
}
```

### Custom Exception Handler
```java
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Handling uncaught exception in async method: " + method.getName());
    }
}
```

## Best Practices

1. **Enable Async Processing Properly**: Always use @EnableAsync on a configuration class
2. **Configure Thread Pools Explicitly**: Avoid relying on the default SimpleAsyncTaskExecutor for production
3. **Set Appropriate Pool Sizes**: Consider your application's needs when configuring core and max pool sizes
4. **Use Meaningful Thread Names**: Set thread name prefixes for easier debugging and monitoring
5. **Handle Rejection Properly**: Configure a rejection policy (e.g., CallerRunsPolicy) to handle task overflow
6. **Be Careful with Transactions**: Remember that transaction context doesn't propagate to async threads
7. **Handle Exceptions Appropriately**:
   - For void methods: Implement AsyncUncaughtExceptionHandler
   - For methods with return values: Handle exceptions when calling Future.get()
8. **Consider Thread Safety**: Ensure that beans used in async methods are thread-safe
9. **Don't Block Unnecessarily**: Avoid blocking operations in async methods when possible
10. **Test Thoroughly**: Async behavior can be difficult to test, so ensure comprehensive testing

## Related Resources
- [Spring @Async Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling)
- [ThreadPoolTaskExecutor Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskExecutor.html)
- [Java Concurrency in Practice](https://jcip.net/) - Comprehensive book on Java concurrency
- [Baeldung Guide to @Async](https://www.baeldung.com/spring-async)
