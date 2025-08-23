package com.example.learningspring.javaConcepts.concurrency.basicthreads;

/**
 * Test class for BasicThreads functionality.
 */
public class BasicThreadsTest {
    
    /**
     * Main method to test the BasicThreads functionality.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Testing Basic Threads:");
        System.out.println("=====================");
        
        // Test the thread interrupt and join functionality
        BasicThreads.demonstrateThreadInterruptAndJoin();
    }
}
