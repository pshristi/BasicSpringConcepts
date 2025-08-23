package com.example.learningspring.javaConcepts.records;

/**
 * Test class for JavaRecords functionality.
 */
public class JavaRecordsTest {
    
    /**
     * Main method to test the JavaRecords functionality.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Create a sample record
        JavaRecords.RateCardLineItemPortingMessage message = JavaRecords.createSampleMessage();
        
        // Print the record
        System.out.println("Sample Record: " + message);
        
        // Access individual fields
        System.out.println("Entity ID: " + message.entityId());
        System.out.println("User: " + message.user());
        System.out.println("Generate RU: " + message.generateRU());
        
        // Demonstrate record equality
        JavaRecords.RateCardLineItemPortingMessage sameMessage = 
            new JavaRecords.RateCardLineItemPortingMessage(
                1, 2, 3, 4, 5, "testUser", 6, true
            );
        
        System.out.println("Records are equal: " + message.equals(sameMessage));
        
        // Different record
        JavaRecords.RateCardLineItemPortingMessage differentMessage = 
            new JavaRecords.RateCardLineItemPortingMessage(
                10, 20, 30, 40, 50, "otherUser", 60, false
            );
        
        System.out.println("Records are different: " + !message.equals(differentMessage));

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        byte[] data = mapper.writeValueAsBytes(message);
        //Send message

        //On receiver side, deserialization
        /RateCardLineItemPortingMessage rateCardLineItemPortingMessage = JsonUtils.fromJson(data, RateCardLineItemPortingMessage.class);
    }
}
