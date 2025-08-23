package com.example.learningspring.javaConcepts.concurrency.producerconsumer;

/**
 * Test class for ProducerConsumer functionality.
 */
public class ProducerConsumerTest {
    
    /**
     * Main method to test the ProducerConsumer functionality.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Testing Producer-Consumer Pattern:");
        System.out.println("=================================");
        
        // Test the producer-consumer functionality
        ProducerConsumer.demonstrateProducerConsumer();
    }
}
