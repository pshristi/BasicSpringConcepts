package com.example.learningspring.javaConcepts.concurrency.producerconsumer;

/**
 * This class demonstrates the Producer-Consumer pattern in Java.
 * It uses wait() and notify() for thread synchronization.
 */
public class ProducerConsumer {
    
    /**
     * A message repository that implements the producer-consumer pattern.
     * It uses wait() and notifyAll() for thread synchronization.
     */
    public static class MessageRepo {
        private String message;
        private boolean hasMessage;
        
        /**
         * Reads a message from the repository.
         * If no message is available, the thread waits until one is produced.
         * 
         * @return The message that was read
         */
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
        
        /**
         * Writes a message to the repository.
         * If a message is already present, the thread waits until it is consumed.
         * 
         * @param message The message to write
         */
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
    
    /**
     * Demonstrates the producer-consumer pattern using the MessageRepo class.
     */
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
}
