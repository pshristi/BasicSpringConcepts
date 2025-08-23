# Basic Threads in Java

## Overview
Java has built-in support for multithreaded programming through the Thread class and the Runnable interface. Threads allow multiple parts of a program to run concurrently, enabling better resource utilization and improved responsiveness in applications.

## Key Features and Benefits
- **Concurrency**: Execute multiple tasks simultaneously
- **Resource utilization**: Make better use of CPU resources
- **Responsiveness**: Keep UI responsive while performing background tasks
- **Simplified programming model**: Built-in language support for multithreading
- **Scalability**: Leverage multi-core processors effectively
- **Asynchronous processing**: Handle operations that may take time to complete

## Thread Lifecycle
A thread in Java goes through various states during its lifecycle:
1. **New**: Thread is created but not yet started
2. **Runnable**: Thread is ready to run and waiting for CPU allocation
3. **Blocked**: Thread is waiting for a monitor lock
4. **Waiting**: Thread is waiting indefinitely for another thread to perform a specific action
5. **Timed Waiting**: Thread is waiting for another thread for a specified period
6. **Terminated**: Thread has completed execution or was terminated

## Example from Code
```java
public static void demonstrateThreadInterruptAndJoin() {
    long time1 = currentTimeMillis();
    
    // Thread interrupt and join
    Thread thread1 = new Thread(() -> {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    });
    
    Thread thread2 = new Thread(() -> {
        for(int i = 0; i < 10; i++) {
            try {
                System.out.println("Thread 2");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    
    Thread thread3 = new Thread(() -> {
        while (true) {
            // Interrupt thread 1 after 5 seconds
            if((currentTimeMillis() - time1) > 5000) {
                thread1.interrupt();
                break;
            }
        }
    });
    
    thread1.start();
    thread3.start();
    
    try {
        thread1.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    if(!thread1.isInterrupted()) {
        thread2.start();
    }
    
    // Print thread states
    System.out.println("\nThread states:");
    printThreadState(thread1);
    printThreadState(thread2);
    printThreadState(thread3);
}
```

## Key Thread Operations Demonstrated

### 1. Thread Creation
```java
Thread thread1 = new Thread(() -> {
    // Thread code here
});
```
- Creates a new thread using a lambda expression as the Runnable
- The thread is in the NEW state at this point

### 2. Thread Starting
```java
thread1.start();
```
- Starts the thread, moving it to the RUNNABLE state
- The JVM allocates resources and calls the thread's run method

### 3. Thread Sleeping
```java
Thread.sleep(1000);
```
- Causes the current thread to pause execution for a specified time
- Moves the thread to the TIMED_WAITING state
- Throws InterruptedException if the thread is interrupted while sleeping

### 4. Thread Interruption
```java
thread1.interrupt();
```
- Interrupts a thread, typically causing it to exit from a blocking operation
- Sets the interrupted status flag of the thread
- If the thread is blocked in a method like sleep(), wait(), or join(), it will throw InterruptedException

### 5. Thread Joining
```java
thread1.join();
```
- Waits for the thread to die
- The calling thread will wait until the joined thread completes
- Useful for coordinating the execution of multiple threads

### 6. Thread State Inspection
```java
printThreadState(thread1);
```
- Retrieves and displays information about the thread's state
- Shows thread ID, name, group, priority, state, and whether it's alive

## Thread States and Transitions
The example demonstrates several thread state transitions:
- NEW → RUNNABLE: When start() is called
- RUNNABLE → TIMED_WAITING: When sleep() is called
- TIMED_WAITING → RUNNABLE: When sleep() completes or is interrupted
- RUNNABLE → TERMINATED: When the thread's run method completes

## Handling InterruptedException
```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    e.printStackTrace();
}
```
- InterruptedException is thrown when a thread is interrupted while in a blocking operation
- Best practice is to re-interrupt the thread by calling Thread.currentThread().interrupt()
- This preserves the interrupted status for higher-level code

## Usage Scenarios
- **Background tasks**: Performing operations without blocking the main thread
- **UI responsiveness**: Keeping user interfaces responsive during long operations
- **Parallel processing**: Dividing work among multiple threads for faster execution
- **Asynchronous I/O**: Handling input/output operations without blocking
- **Timer tasks**: Executing tasks at specific intervals
- **Resource monitoring**: Monitoring system resources in the background

## Additional Information
- Threads in Java are objects of the Thread class
- The Runnable interface provides a way to define the code that a thread will execute
- Java 8+ allows using lambda expressions as Runnables
- Thread priorities range from 1 (lowest) to 10 (highest)
- Thread scheduling is platform-dependent
- Daemon threads are background threads that don't prevent the JVM from exiting
- Thread safety is a critical concern in multithreaded applications
