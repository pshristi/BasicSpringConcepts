package com.example.learningspring.javaConcepts.streams;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * This class demonstrates the use of Java Streams.
 * Streams provide a high-level abstraction for processing sequences of elements.
 */
public class JavaStreams {
    
    /**
     * Demonstrates different ways to use Java Streams.
     */
    public static void demonstrateStreams() {
        // Concat streams
        System.out.println("Concatenating streams:");
        var stream1 = Stream.of("a", "b", "c")
                .map(String::toUpperCase);
        var stream2 = Arrays.stream(new String[]{"d", "e", "f"})
                .map(a -> a + "-");
        Stream.concat(stream1, stream2)
                .filter(a -> a.length() > 1)
                .forEach(System.out::println);
        
        // Generate infinite stream with random numbers
        System.out.println("\nGenerating infinite stream with random numbers (limited to 5):");
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(5)
                .forEach(System.out::println);
        
        // Generate infinite stream with sequential numbers
        System.out.println("\nGenerating infinite stream with sequential numbers (limited to 5):");
        Stream.iterate(0, n -> n + 1)
                .limit(5)
                .forEach(System.out::println);
        
        // Additional stream operations
        System.out.println("\nAdditional stream operations:");
        
        // Filter and map
        System.out.println("Filter and map:");
        Stream.of(1, 2, 3, 4, 5, 6)
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .forEach(System.out::println);
        
        // Reduce
        System.out.println("\nReduce operation:");
        int sum = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, Integer::sum);
        System.out.println("Sum of 1 to 5: " + sum);
    }
}
