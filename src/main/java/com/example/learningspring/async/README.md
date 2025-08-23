# Asynchronous Processing in Spring

This package demonstrates various approaches to asynchronous processing in Spring Framework, which allows methods to be executed in separate threads, improving application responsiveness and scalability.

## What is Asynchronous Processing?

In a typical synchronous application, when a method is called, the caller thread waits until the method completes before continuing. This can lead to performance bottlenecks, especially for time-consuming operations.

With asynchronous processing:
- Methods run in separate threads
- The caller thread doesn't wait for the method to complete
- The application can handle more concurrent operations
- User experience improves as the UI remains responsive

## How to Enable Async Processing in Spring

To use async processing in Spring, you need to:

> **Note**: As mentioned in the code comments, `@EnableAsync` annotation is required for async methods to work. Without this annotation, methods marked with `@Async` will execute synchronously.

1. Add the `@EnableAsync` annotation to a configuration class:
   ```java
   @Configuration
   @EnableAsync
   public class AppConfig {
       // Configuration details
   }
   ```

2. Add the `@Async` annotation to methods you want to run asynchronously:
   ```java
   @Service
   public class MyService {
       @Async
       public void processData() {
           // This will run in a separate thread
       }
   }
   ```

## Key Concepts Demonstrated

### 1. Asynchronous Method Execution

#### Basic @Async Usage

From our `defaultThreadPoolTaskExecutor` package:

```java
@Service
public class AsyncTestService {
    @Async
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }
}
```

When this method is called from a controller:

```java
@RestController
public class AsyncTestRestController {
    @Autowired
    AsyncTestService asyncTestService;

    @GetMapping("/getUser")
    public String getUser(){
        System.out.println("Inside getUser() method : " + Thread.currentThread().getName());
        // This call returns immediately, doesn't wait for getAsyncData() to complete
        asyncTestService.getAsyncData();
        return "User Name: John Doe";
    }
}
```

The controller's thread continues execution immediately after calling `getAsyncData()`, without waiting for it to complete. The async method runs in a separate thread.

### 2. Thread Pool Configuration Options

Spring provides several ways to configure thread pools for async execution:

#### Default Thread Pool

Without explicit configuration, Spring uses `SimpleAsyncTaskExecutor` which creates a new thread for each task:

```java
// No explicit configuration needed
@Async
void getAsyncData() {
    // This runs in a thread created by SimpleAsyncTaskExecutor
    System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
}
```

**How Spring selects the executor**: As noted in the code comments, on startup, Spring's `AsyncExecutionInterceptor` tries to find any `ThreadPoolTaskExecutor` bean (by calling `getDefaultExecutor()`). If it doesn't find one, it falls back to using `SimpleAsyncTaskExecutor`.

**Note**: The default executor creates a new thread for each invocation, which is not ideal for production environments.

#### Custom Thread Pool

For better control, you can create a custom thread pool:

```java
@Configuration
public class AppConfig {
    @Bean
    public Executor customTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);  // Minimum number of threads to keep alive
        executor.setMaxPoolSize(5);   // Maximum number of threads to allow
        executor.setQueueCapacity(3); // How many tasks to queue before rejecting
        executor.setThreadNamePrefix("Custom-Thread-");  // For easier debugging
        executor.initialize();
        return executor;
    }
}
```

To use this custom executor with specific methods:

```java
@Service
public class AsyncTestService1 {
    @Async("customTaskExecutor")  // Specify which executor to use
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
        // Output will show thread name like: Custom-Thread-1
    }
}
```

**Important Note**: If you provide a `ThreadPoolTaskExecutor` bean, both `@Async("customTaskExecutor")` and plain `@Async` will work with it. However, as noted in the code comments, if you provide a `ThreadPoolExecutor` rather than `ThreadPoolTaskExecutor`, then only the explicitly named executor (e.g., `@Async("customTaskExecutor")`) will work. If you use plain `@Async` in this case, Spring will fall back to using `SimpleAsyncTaskExecutor`.

#### Best Practices for Configuration

For production applications, implement `AsyncConfigurer` for more control:

```java
@Configuration
public class AppConfig1 implements AsyncConfigurer {
    private ThreadPoolExecutor poolExecutor;

    @Override
    public synchronized Executor getAsyncExecutor() {
        if(poolExecutor == null) {
            poolExecutor = new ThreadPoolExecutor(3, 5, 60L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10));

            // Handle task overflow by running in the caller's thread
            poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return poolExecutor;
    }
}
```

This approach provides:
- Thread-safe initialization with `synchronized`
- Proper rejection handling with `CallerRunsPolicy`
- A single place to configure all async execution

With this configuration, you can use the simple `@Async` annotation without specifying an executor name:

```java
@Service
public class AsyncTestService2 {
    @Async  // Uses the executor from AsyncConfigurer
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }
}
```

### 3. Exception Handling in Async Methods

Handling exceptions in async methods requires special attention because they occur in a separate thread. As noted in the code comments, the approach to handling exceptions differs based on whether the async method returns a value or not:

#### For Void Methods

For methods that don't return a value, you need to implement `AsyncUncaughtExceptionHandler`. As the code comments explain, when an async method doesn't have a return type, you must implement the `handleUncaughtException` method of `AsyncUncaughtExceptionHandler` to handle exceptions:

```java
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Handling uncaught exception in async method: " + method.getName());
        // Log the exception, send notifications, etc.
    }
}
```

Register your handler by implementing `AsyncConfigurer`:

```java
@Configuration
public class AppConfig2 implements AsyncConfigurer {
    @Autowired
    CustomAsyncExceptionHandler customAsyncExceptionHandler;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return this.customAsyncExceptionHandler;
    }
}
```

#### For Methods with Return Values

For methods that return a value, exceptions are propagated through the `Future` object. As the code comments explain, when an async method has a return type, you can catch any exceptions in the caller method when you call `Future.get()`:

```java
@RestController
public class AsyncTestController3 {
    @Autowired
    AsyncTestService3 asyncTestService3;

    @GetMapping("/getUser")
    public String getUser() {
        Future<String> result = asyncTestService3.getAsyncData();

        String resultString = "User data not available";
        try {
            resultString = result.get();  // Exceptions from the async method are thrown here
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return resultString;
    }
}
```

### 4. Return Values with Future

To get results from async methods, use `Future` or its subclasses:

```java
@Service
public class AsyncTestService3 {
    @Async
    public Future<String> getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
        // Perform some async operation
        return new AsyncResult<>("Async data returned");  // Wrap the result
    }
}
```

The caller can then wait for and retrieve the result:

```java
Future<String> result = asyncTestService3.getAsyncData();
// Do other work while the async method is running

// When you need the result:
try {
    String data = result.get();  // This blocks until the result is available
    // Use the data
} catch (Exception e) {
    // Handle exceptions
}
```

### 5. Transactions in Async Methods

There's an important limitation when using transactions with async methods:

```java
// From asyncWithTransaction.java
// Transaction context doesn't get transferred to async threads from caller thread
```

This means:
- If a method is annotated with both `@Async` and `@Transactional`, the transaction won't work as expected
- If an `@Async` method is called from a `@Transactional` method, the transaction context isn't propagated

To handle transactions in async scenarios:
1. Create a separate transactional method that doesn't use `@Async`
2. Call this transactional method from your async method
3. Or use programmatic transaction management within the async method

## Best Practices

1. **Enable Async Processing Properly**: 
   Always use `@EnableAsync` on a configuration class.

2. **Configure Thread Pools Explicitly**: 
   Avoid relying on the default `SimpleAsyncTaskExecutor` for production.
   ```java
   @Bean
   public Executor taskExecutor() {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(5);
       executor.setMaxPoolSize(10);
       executor.setQueueCapacity(25);
       executor.initialize();
       return executor;
   }
   ```

3. **Set Appropriate Pool Sizes**: 
   Consider your application's needs when configuring core and max pool sizes.
   - Too small: Underutilizes resources
   - Too large: Wastes resources and increases context switching

4. **Use Meaningful Thread Names**: 
   Set thread name prefixes for easier debugging and monitoring.
   ```java
   executor.setThreadNamePrefix("MyApp-Async-");
   ```

5. **Handle Rejection Properly**: 
   Configure a rejection policy to handle task overflow.
   ```java
   executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
   ```

6. **Be Careful with Transactions**: 
   Remember that transaction context doesn't propagate to async threads.

7. **Handle Exceptions Appropriately**:
   - For void methods: Implement `AsyncUncaughtExceptionHandler`
   - For methods with return values: Handle exceptions when calling `Future.get()`

8. **Consider Thread Safety**: 
   Ensure that beans used in async methods are thread-safe.

9. **Don't Block Unnecessarily**: 
   Avoid blocking operations in async methods when possible.

10. **Test Thoroughly**: 
    Async behavior can be difficult to test, so ensure comprehensive testing.

## Related Resources
- [Spring @Async Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling)
- [ThreadPoolTaskExecutor Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskExecutor.html)
- [Java Concurrency in Practice](https://jcip.net/) - Comprehensive book on Java concurrency
- [Baeldung Guide to @Async](https://www.baeldung.com/spring-async)
