package com.example.learningspring.javaConcepts.records;

/**
 * This class demonstrates the Java Records feature.
 * Records provide a compact syntax for declaring classes that are transparent holders for shallowly immutable data.
 */
public class JavaRecords {
    
    /**
     * A record representing a message for rate card line item porting.
     */
    public record RateCardLineItemPortingMessage(Integer entityId, Integer entityTypeId,
                                                 Integer sourceEntityId,
                                                 Integer sourceEntityTypeId,
                                                 Integer clientId,
                                                 String user,
                                                 Integer userId,
                                                 boolean generateRU) {}
    
    /**
     * Creates a new RateCardLineItemPortingMessage with sample data.
     * 
     * @return A new RateCardLineItemPortingMessage instance
     */
    public static RateCardLineItemPortingMessage createSampleMessage() {
        return new RateCardLineItemPortingMessage(
            1, 2, 3, 4, 5, "testUser", 6, true
        );
    }
}
