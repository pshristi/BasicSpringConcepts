# Thread Pools in Java and Spring

This package demonstrates the implementation and configuration of thread pools in Java, which are a fundamental concurrency mechanism used in Spring applications for efficient thread management and task execution.

## Key Concepts Demonstrated

### Thread Pool Fundamentals
- **Thread Reuse**: Reusing threads to reduce the overhead of thread creation and destruction
- **Task Queuing**: Managing tasks when all threads are busy
- **Pool Sizing**: Configuring core and maximum pool sizes
- **Thread Lifecycle**: Managing thread creation, usage, and termination

### ThreadPoolExecutor Configuration
The `testThreadPoolExecutor.java` file demonstrates how to create and configure a ThreadPoolExecutor with specific parameters:

- **Core Pool Size**: The minimum number of threads to keep in the pool (2 in our example)
- **Maximum Pool Size**: The maximum number of threads allowed in the pool (3 in our example)
- **Keep-Alive Time**: How long idle threads should be kept alive (1 minute in our example)
- **Work Queue**: The queue used to hold tasks before they are executed (LinkedBlockingQueue with capacity 2 in our example)
- **Thread Factory**: Factory for creating new threads (defaultThreadFactory in our example)
- **Rejection Policy**: How to handle tasks when the queue is full (CallerRunsPolicy in our example)

When you run the example in `testThreadPoolExecutor.java`, here's what happens:
1. A thread pool is created with 2 core threads, 3 max threads, and a queue capacity of 2
2. 10 tasks are submitted to the pool
3. The first 2 tasks are assigned to the core threads
4. The next 2 tasks are placed in the queue
5. The next task causes the pool to create a new thread (up to max size)
6. When all threads are busy and the queue is full, the CallerRunsPolicy makes the submitting thread run the task

### Custom Components
The package also demonstrates how to create custom components for thread pools:

- **Custom Thread Factory**: Creating threads with specific names, daemon status, and priorities
- **Custom Rejection Handler**: Handling rejected tasks when the queue is full

While these custom components are defined in the `testThreadPoolExecutor.java` file, they are not used in the main method. To use them, you would replace the default components in the ThreadPoolExecutor constructor like this:

```java
// Create a custom thread factory
CustomThreadFactory threadFactory = new CustomThreadFactory();

// Create a custom rejection handler
CustomRejectedExecutionHandler rejectionHandler = new CustomRejectedExecutionHandler();

// Use them in the ThreadPoolExecutor constructor
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                          // Core pool size
    3,                          // Maximum pool size
    1, TimeUnit.MINUTES,        // Keep-alive time
    new LinkedBlockingQueue<>(2), // Work queue
    threadFactory,              // Custom thread factory
    rejectionHandler            // Custom rejection handler
);
```

## Code Examples

### Creating a ThreadPoolExecutor
```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                                      // Core pool size
    3,                                      // Maximum pool size
    1, TimeUnit.MINUTES,                    // Keep-alive time
    new LinkedBlockingQueue<>(2),           // Work queue
    Executors.defaultThreadFactory(),       // Thread factory
    new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy
);
```

### Configuring Thread Pool Behavior
```java
// Allow core threads to time out when idle
executor.allowCoreThreadTimeOut(true);
```

### Submitting Tasks to the Thread Pool
```java
for (int i = 0; i < 10; i++) {
    executor.execute(() -> {
        try {
            Thread.sleep(5000);
            System.out.println("Thread: " + Thread.currentThread().getName() + " is running");
        }
        catch (Exception e) {
            System.out.println("Error in thread: " + Thread.currentThread().getName());
            e.printStackTrace();
        }
    });
}
```

### Shutting Down the Thread Pool
```java
executor.shutdown();
```

### Custom Thread Factory
```java
public class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "Custom Thread");
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
```

### Custom Rejection Handler
```java
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected: " + r.toString());
    }
}
```

## Thread Pool Types in Java

Java provides several predefined thread pool implementations through the `Executors` utility class:

1. **Fixed Thread Pool**: Fixed number of threads, tasks queue up when all threads are busy
   ```java
   ExecutorService fixedPool = Executors.newFixedThreadPool(5);
   ```

2. **Cached Thread Pool**: Dynamically creates new threads as needed, reuses idle threads
   ```java
   ExecutorService cachedPool = Executors.newCachedThreadPool();
   ```

3. **Single Thread Executor**: Uses a single worker thread to execute tasks sequentially
   ```java
   ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
   ```

4. **Scheduled Thread Pool**: Allows scheduling tasks to run after a delay or periodically
   ```java
   ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(3);
   ```

## Thread Pools in Spring

Spring provides several abstractions for working with thread pools:

1. **TaskExecutor Interface**: Spring's abstraction for thread pools
2. **ThreadPoolTaskExecutor**: Implementation that wraps a ThreadPoolExecutor
3. **@Async Annotation**: For executing methods asynchronously using thread pools
4. **Customization Options**: Spring Boot properties for configuring thread pools

## Common Use Cases

1. **Parallel Processing**: Executing multiple tasks concurrently
2. **Asynchronous Operations**: Performing non-blocking operations
3. **Background Tasks**: Running maintenance or cleanup tasks
4. **Scheduled Jobs**: Executing tasks at specific intervals
5. **Web Request Handling**: Processing multiple web requests concurrently
6. **Resource Management**: Limiting concurrent access to resources
7. **Batch Processing**: Processing large datasets in parallel

## Best Practices

1. **Size Appropriately**: Set core and max pool sizes based on your application's needs and available resources
2. **Use Bounded Queues**: Limit queue sizes to prevent out-of-memory errors
3. **Choose Appropriate Rejection Policies**: Select a rejection policy that fits your application's requirements
4. **Monitor Thread Pool Performance**: Track queue size, active thread count, and task completion times
5. **Avoid Thread Starvation**: Ensure long-running tasks don't prevent other tasks from executing
6. **Handle Exceptions Properly**: Catch and handle exceptions within tasks to prevent thread termination
7. **Shut Down Properly**: Always shut down thread pools when they're no longer needed
8. **Avoid Common Pitfalls**:
   - Don't use unbounded queues with unlimited threads
   - Don't block the main thread waiting for task completion
   - Don't use thread pools for CPU-intensive tasks with a thread count higher than available cores

## Related Resources
- [Java ThreadPoolExecutor Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ThreadPoolExecutor.html)
- [Spring TaskExecutor Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-task-executor)
- [Java Concurrency in Practice](https://jcip.net/) - Comprehensive book on Java concurrency
- [Spring @Async Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling)
- [Thread Pool Best Practices](https://www.baeldung.com/thread-pool-java-and-guava)
