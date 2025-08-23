package com.example.learningspring.javaConcepts.enhancedswitch;

/**
 * This class demonstrates the enhanced switch feature in Java.
 */
public class EnhancedSwitch {
    
    /**
     * Returns the quarter of the year based on the given month.
     * 
     * @param month The month name
     * @return The quarter as a string
     */
    public static String getQuarter(String month) {
        return switch (month) {
            case "January", "February", "March" -> {yield "1st";}
            case "April", "May", "June" -> "2nd";
            case "July", "August", "September" -> "3rd";
            case "October", "November", "December" -> "4th";
            default -> "Invalid month";
        };
    }
}
