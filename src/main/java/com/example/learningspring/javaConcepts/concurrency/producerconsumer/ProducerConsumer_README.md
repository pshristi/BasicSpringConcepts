# Producer-Consumer Pattern in Java

## Overview
The Producer-Consumer pattern is a classic concurrency design pattern that coordinates the interaction between threads that produce data (producers) and threads that consume data (consumers). It uses a shared buffer or repository to transfer data between threads, with synchronization mechanisms to ensure thread safety.

## Key Features and Benefits
- **Decoupling**: Separates the production of data from its consumption
- **Thread coordination**: Synchronizes producer and consumer threads
- **Resource management**: Controls access to shared resources
- **Flow control**: Prevents producers from overwhelming consumers
- **Parallel processing**: Enables efficient parallel execution of tasks
- **Bounded buffer**: Can limit the amount of data in transit

## Thread Synchronization Mechanisms
The example demonstrates thread synchronization using:
- **synchronized methods**: To ensure mutual exclusion
- **wait()**: To make a thread wait until a condition is met
- **notifyAll()**: To wake up all waiting threads when a condition changes
- **while loops**: To check conditions in a way that prevents spurious wakeups
- **Reentrant Lock and Deadlock control with wait and notify demonstrated here**

## Example from Code
```java
public static class MessageRepo {
    private String message;
    private boolean hasMessage;

    public synchronized String read() {
        while(!hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hasMessage = false;
        System.out.println("Consumer read: " + message);
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while(hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hasMessage = true;
        this.message = message;
        System.out.println("Producer wrote: " + message);
        notifyAll();
    }
}
```

## Demonstration of the Pattern
```java
public static void demonstrateProducerConsumer() {
    MessageRepo messageRepo = new MessageRepo();
    String text = """
            Line 1
            Line 2
            Line 3
            """;
    String[] textArray = text.split("\n");

    // Consumer thread
    Thread reader = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String message = messageRepo.read();
            if("Finished".equals(message)) {
                System.out.println("Consumer finished");
                break;
            }
        }
    });

    // Producer thread
    Thread writer = new Thread(() -> {
        for (int i = 0; i < textArray.length; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageRepo.write(textArray[i]);
        }
        messageRepo.write("Finished");
        System.out.println("Producer finished");
    });

    // Start both threads
    System.out.println("Starting producer and consumer threads");
    reader.start();
    writer.start();

    // Wait for both threads to complete
    try {
        reader.join();
        writer.join();
        System.out.println("Both threads have completed");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

## How the Pattern Works in This Example
1. **Shared Resource**: The `MessageRepo` class acts as a shared buffer with a single message slot
2. **Synchronization**: Both `read()` and `write()` methods are synchronized to ensure mutual exclusion
3. **Condition Variables**:
   - `hasMessage` indicates whether a message is available for reading
   - Producers wait when `hasMessage` is true (buffer full)
   - Consumers wait when `hasMessage` is false (buffer empty)
4. **Producer Thread**:
   - Writes messages to the repository one at a time
   - Waits if the previous message hasn't been consumed
   - Signals consumers when a new message is available
5. **Consumer Thread**:
   - Reads messages from the repository
   - Waits if no message is available
   - Signals producers when a message has been consumed
6. **Termination Signal**:
   - A special "Finished" message is used to signal the end of production
   - The consumer exits its loop when it receives this message

## Key Synchronization Concepts
- **synchronized keyword**: Ensures that only one thread can execute a method at a time
- **wait()**: Releases the lock and puts the thread in a waiting state
- **notifyAll()**: Wakes up all threads that are waiting on this object's monitor
- **while loop for conditions**: Guards against spurious wakeups by rechecking the condition after waking up

## Variations of the Pattern
- **Bounded Buffer**: Limits the number of items in the buffer (not shown in this example)
- **Multiple Producers/Consumers**: Multiple threads producing or consuming data
- **Priority-based**: Producers or consumers with different priorities
- **Blocking Queue**: Using Java's BlockingQueue implementations (e.g., ArrayBlockingQueue)

## Modern Alternatives in Java
- **BlockingQueue**: Java's built-in implementation of the producer-consumer pattern
  ```java
  BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
  queue.put("message"); // Blocks if queue is full
  String message = queue.take(); // Blocks if queue is empty
  ```
- **CompletableFuture**: For asynchronous producer-consumer scenarios
- **java.util.concurrent.Flow**: Reactive Streams API for asynchronous stream processing

## Usage Scenarios
- **Work queues**: Tasks produced by one thread and executed by others
- **Data pipelines**: Processing data in stages
- **Event handling**: Producing and consuming events
- **Buffering**: Managing data flow between components with different processing rates
- **Resource pools**: Managing limited resources
- **Message passing**: Communication between threads or processes

## Additional Information
- The pattern was first described in "The Mythical Man-Month" by Fred Brooks
- It's a fundamental pattern in concurrent and distributed systems
- Java's BlockingQueue interface provides ready-to-use implementations
- For complex scenarios, consider using higher-level concurrency utilities
- The pattern can be extended to multiple producers and consumers
- Proper error handling is crucial in production implementations
