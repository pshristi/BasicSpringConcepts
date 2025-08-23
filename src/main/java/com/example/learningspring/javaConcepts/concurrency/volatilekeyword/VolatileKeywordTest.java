package com.example.learningspring.javaConcepts.concurrency.volatilekeyword;

/**
 * Test class for VolatileKeyword functionality.
 */
public class VolatileKeywordTest {
    
    /**
     * Main method to test the VolatileKeyword functionality.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Testing Volatile Keyword:");
        System.out.println("========================");
        
        // Test the issue with non-volatile variables
        System.out.println("\nDemonstrating issue without volatile:");
        System.out.println("-----------------------------------");
        VolatileKeyword.demonstrateVolatileIssue();
        
        // Test the fixed version with volatile
        System.out.println("\nDemonstrating fixed version with volatile:");
        System.out.println("----------------------------------------");
        VolatileKeyword.demonstrateVolatileFixed();
    }
}
