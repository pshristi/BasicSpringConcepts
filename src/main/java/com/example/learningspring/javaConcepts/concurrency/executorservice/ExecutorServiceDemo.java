package com.example.learningspring.javaConcepts.concurrency.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

/**
 * This class demonstrates the use of ExecutorService in Java.
 * ExecutorService provides a high-level API for executing tasks asynchronously.
 */
public class ExecutorServiceDemo {
    
    /**
     * Demonstrates different types of executor services and their usage.
     */
    public static void demonstrateExecutorService() {
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
        
        var executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(() -> {
            System.out.println("Thread 2");
        });
        executor2.shutdown();
        
        // Fixed Thread Pool (keep alive till lifecycle of app)
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
        
        System.out.println("Waiting for scheduled task to complete...");
        long time = currentTimeMillis();
        while(currentTimeMillis() - time < 5000) {
            if(result.isDone()) {
                try {
                    System.out.println("Result: " + result.get());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        result.cancel(true);
        executor6.shutdown();
        
        // Wait for all executors to terminate
        try {
            if (!executor3.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Executor 3 did not terminate in the specified time.");
            }
            if (!executor4.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Executor 4 did not terminate in the specified time.");
            }
            if (!executor5.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Executor 5 did not terminate in the specified time.");
            }
            if (!executor6.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Executor 6 did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
