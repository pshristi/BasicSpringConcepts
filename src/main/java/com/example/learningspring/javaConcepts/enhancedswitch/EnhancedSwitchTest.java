package com.example.learningspring.javaConcepts.enhancedswitch;

/**
 * Test class for EnhancedSwitch functionality.
 */
public class EnhancedSwitchTest {
    
    /**
     * Main method to test the EnhancedSwitch functionality.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Test the enhanced switch functionality
        System.out.println("Hello World " + EnhancedSwitch.getQuarter("January"));
        
        // Test other months
        System.out.println("April is in the " + EnhancedSwitch.getQuarter("April") + " quarter");
        System.out.println("July is in the " + EnhancedSwitch.getQuarter("July") + " quarter");
        System.out.println("October is in the " + EnhancedSwitch.getQuarter("October") + " quarter");
        System.out.println("Invalid month is in the " + EnhancedSwitch.getQuarter("InvalidMonth") + " quarter");
    }
}
