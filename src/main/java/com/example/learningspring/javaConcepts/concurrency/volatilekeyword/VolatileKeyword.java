package com.example.learningspring.javaConcepts.concurrency.volatilekeyword;

/**
 * This class demonstrates the use of the volatile keyword in Java.
 * The volatile keyword ensures that changes to a variable are visible to all threads.
 */
public class VolatileKeyword {
    
    /**
     * A class that demonstrates the need for the volatile keyword.
     * Without volatile, changes to the flag variable might not be visible to other threads.
     */
    public static class TestVolatile {
        // This should ideally be marked as volatile to ensure visibility across threads
        boolean flag = false;
        
        /**
         * Toggles the flag value.
         */
        public void toggleFlag() {
            System.out.println("Current flag value: " + flag);
            this.flag = !flag;
            System.out.println("Flag toggled to: " + flag);
        }
        
        /**
         * Returns the current flag value.
         * 
         * @return The current flag value
         */
        public boolean isFlag() {
            return flag;
        }
    }
    
    /**
     * Demonstrates the issue with non-volatile variables in a multi-threaded environment.
     * Without volatile, thread2 might never see the updated flag value from thread1.
     */
    public static void demonstrateVolatileIssue() {
        TestVolatile testVolatile = new TestVolatile();
        
        Thread thread1 = new Thread(() -> {
            try {
                // Sleep to allow thread2 to start and enter its loop
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1: About to toggle flag");
            testVolatile.toggleFlag();
        });
        
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: Waiting for flag to become true");
            // This loop might never exit without volatile
            int count = 0;
            while (!testVolatile.isFlag()) {
                // Add a small delay to make the issue less likely to occur
                if (count++ % 1000000 == 0) {
                    System.out.println("Thread 2: Still waiting...");
                }
            }
            System.out.println("Thread 2: Flag is now true, exiting");
        });
        
        thread2.start();
        thread1.start();
        
        try {
            // Wait for both threads to complete
            thread1.join();
            thread2.join(5000); // Wait up to 5 seconds for thread2
            
            // Check if thread2 is still running (stuck in the loop)
            if (thread2.isAlive()) {
                System.out.println("Thread 2 is still running after 5 seconds - this demonstrates the volatile issue");
                thread2.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * A corrected version of TestVolatile using the volatile keyword.
     */
    public static class TestVolatileFixed {
        // The volatile keyword ensures visibility across threads
        private volatile boolean flag = false;
        
        public void toggleFlag() {
            System.out.println("Current flag value: " + flag);
            this.flag = !flag;
            System.out.println("Flag toggled to: " + flag);
        }
        
        public boolean isFlag() {
            return flag;
        }
    }
    
    /**
     * Demonstrates the correct behavior with volatile variables.
     */
    public static void demonstrateVolatileFixed() {
        TestVolatileFixed testVolatile = new TestVolatileFixed();
        
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1: About to toggle flag");
            testVolatile.toggleFlag();
        });
        
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: Waiting for flag to become true");
            while (!testVolatile.isFlag()) {
                // This loop will exit once the flag is changed
            }
            System.out.println("Thread 2: Flag is now true, exiting");
        });
        
        thread2.start();
        thread1.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
