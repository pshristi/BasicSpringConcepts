# Executor Service in Java

## Overview
The ExecutorService is part of Java's concurrency framework (java.util.concurrent) introduced in Java 5. It provides a high-level API for asynchronously executing tasks, abstracting away the complexities of thread creation, management, and scheduling. ExecutorService represents a thread pool that manages a collection of worker threads.

## Key Features and Benefits
- **Thread pool management**: Reuses existing threads rather than creating new ones
- **Task queuing**: Manages tasks that cannot be executed immediately
- **Lifecycle management**: Provides methods to control the lifecycle of the thread pool
- **Future results**: Supports asynchronous computation with result retrieval
- **Scheduled execution**: Supports delayed and periodic task execution
- **Reduced resource consumption**: Limits the number of threads created
- **Improved performance**: Eliminates the overhead of thread creation
- **Graceful shutdown**: Provides mechanisms for orderly shutdown

## Types of Executor Services
Java provides several types of executor services through the Executors factory class:

1. **SingleThreadExecutor**: Uses a single worker thread
2. **FixedThreadPool**: Uses a fixed number of worker threads
3. **CachedThreadPool**: Creates new threads as needed, reuses idle threads
4. **ScheduledThreadPool**: Supports scheduled and periodic task execution
5. **WorkStealingPool** (Java 8+): Uses a work-stealing algorithm with multiple queues

## Example from Code
```java
// Single thread executor
System.out.println("Single Thread Executor:");
var executor1 = Executors.newSingleThreadExecutor();
executor1.execute(() -> {
    System.out.println("Thread 1");
});
executor1.shutdown();

// Equivalent to call thread1.join()
try {
    executor1.awaitTermination(500, TimeUnit.MILLISECONDS);
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Fixed Thread Pool
System.out.println("\nFixed Thread Pool:");
var executor3 = Executors.newFixedThreadPool(3);

// Submitting tasks to thread pool (will be executed in FIFO order via thread pool manager)
for(int i = 0; i < 6; i++) {
    int finalI = i;
    executor3.submit(() -> {System.out.println("Thread " + finalI);});
}
executor3.shutdown();

// Cached Thread Pool
System.out.println("\nCached Thread Pool:");
var executor4 = Executors.newCachedThreadPool();
for(int i = 0; i < 2; i++) {
    int finalI = i;
    executor4.submit(() -> {System.out.println("Thread " + finalI);});
}
executor4.shutdown();

// Executor with Future
System.out.println("\nExecutor with Future:");
// By default future get() is blocking
var executor5 = Executors.newCachedThreadPool();
List<Callable<String>> tasks = new ArrayList<>();
tasks.add(() -> {return "Thread1";});
tasks.add(() -> {return "Thread2";});
tasks.add(() -> {return "Thread3";});

try{
    // Can use submit() in a loop for each task
    var results = executor5.invokeAll(tasks);
    for(var result : results) {
        System.out.println(result.get());
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    executor5.shutdown();
}

// Scheduled Executor
System.out.println("\nScheduled Executor:");
// Executor6 tasks will run with a total delay of 3 (2+1) seconds
ScheduledExecutorService executor6 = Executors.newScheduledThreadPool(2);
ScheduledFuture<?> result = executor6.schedule(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Scheduled task executed after delay");
            return "Task completed";
        },
        2,
        TimeUnit.SECONDS);
```

## Different Types of Executor Services Demonstrated

### 1. SingleThreadExecutor
```java
var executor1 = Executors.newSingleThreadExecutor();
```
- Uses a single worker thread operating off an unbounded queue
- Tasks are guaranteed to execute sequentially
- Good for tasks that must be executed in a specific order

### 2. FixedThreadPool
```java
var executor3 = Executors.newFixedThreadPool(3);
```
- Creates a thread pool with a fixed number of threads
- Tasks are queued if all threads are busy
- Good for limiting resource usage and controlling concurrency level

### 3. CachedThreadPool
```java
var executor4 = Executors.newCachedThreadPool();
```
- Creates new threads as needed and reuses idle threads
- Threads that remain idle for 60 seconds are terminated
- Good for many short-lived tasks

### 4. ScheduledThreadPool
```java
ScheduledExecutorService executor6 = Executors.newScheduledThreadPool(2);
```
- Supports delayed and periodic task execution
- Creates a thread pool with a specified number of threads
- Good for tasks that need to run after a delay or periodically

## Task Submission Methods
The example demonstrates several ways to submit tasks:

### 1. execute()
```java
executor1.execute(() -> { System.out.println("Thread 1"); });
```
- Executes the task at some point in the future
- Does not return a result
- Does not provide a way to check if the task completed

### 2. submit()
```java
executor3.submit(() -> { System.out.println("Thread " + finalI); });
```
- Submits a task for execution
- Returns a Future representing the pending result
- Can be used with Runnable or Callable tasks

### 3. invokeAll()
```java
var results = executor5.invokeAll(tasks);
```
- Executes a collection of tasks
- Returns a list of Futures holding the results
- Waits for all tasks to complete

### 4. schedule()
```java
ScheduledFuture<?> result = executor6.schedule(task, 2, TimeUnit.SECONDS);
```
- Schedules a task for execution after a specified delay
- Returns a ScheduledFuture representing the pending result
- Can be used to cancel the task or check if it's done

## Working with Futures
```java
var results = executor5.invokeAll(tasks);
for(var result : results) {
    System.out.println(result.get());
}
```
- Future represents the result of an asynchronous computation
- get() blocks until the result is available
- isDone() checks if the computation is complete
- cancel() attempts to cancel the execution

## Proper Shutdown
```java
executor1.shutdown();
if (!executor1.awaitTermination(500, TimeUnit.MILLISECONDS)) {
    System.out.println("Executor did not terminate in the specified time.");
}
```
- shutdown() initiates an orderly shutdown
- awaitTermination() blocks until all tasks have completed or the timeout occurs
- shutdownNow() attempts to stop all actively executing tasks

## Usage Scenarios
- **Web servers**: Processing HTTP requests concurrently
- **Background tasks**: Running maintenance tasks in the background
- **Parallel processing**: Dividing work among multiple threads
- **Task scheduling**: Running tasks at specific times or intervals
- **Asynchronous programming**: Executing tasks without blocking the main thread
- **Resource pooling**: Managing a pool of resources like database connections

## Best Practices
1. **Always shutdown executors**: Failing to do so can prevent the JVM from exiting
2. **Use try-finally blocks**: Ensure executors are shut down even if exceptions occur
3. **Set appropriate pool sizes**: Too many threads can degrade performance
4. **Handle exceptions in tasks**: Uncaught exceptions in tasks are swallowed by the executor
5. **Use appropriate executor type**: Choose the right executor for your use case
6. **Avoid blocking operations in tasks**: They can tie up worker threads
7. **Consider using higher-level abstractions**: CompletableFuture, parallel streams, etc.

## Additional Information
- ExecutorService was introduced in Java 5 (2004)
- It's part of the java.util.concurrent package
- The Executors class provides factory methods for creating different types of executors
- For production use, consider using ThreadPoolExecutor directly for more control
- Java 8 introduced CompletableFuture which builds on ExecutorService
- Java 9 introduced the Flow API for reactive programming
