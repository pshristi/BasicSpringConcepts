package com.example.learningspring.javaConcepts.concurrency.basicthreads;

import static java.lang.System.currentTimeMillis;

/**
 * This class demonstrates basic thread operations in Java.
 * It covers thread creation, starting, interrupting, and joining.
 */
public class BasicThreads {
    
    /**
     * Demonstrates thread interrupt and join operations.
     */
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
    
    /**
     * Prints detailed information about a thread's state.
     * 
     * @param thread The thread to print information about
     */
    public static void printThreadState(Thread thread) {
        System.out.println("---------------------");
        System.out.println("THREAD ID" + " : " + thread.getId());
        System.out.println("THREAD NAME" + " : " + thread.getName());
        System.out.println("THREAD GROUP" + " : " + thread.getThreadGroup());
        System.out.println("THREAD PRIORITY" + " : " + thread.getPriority());
        System.out.println("THREAD STATE" + " : " + thread.getState());
        System.out.println("THREAD ISALIVE" + " : " + thread.isAlive());
        System.out.println("---------------------");
    }
}
