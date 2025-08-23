package com.example.learningspring.javaConcepts.methodreference;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class demonstrates the use of Method References in Java.
 * Method references are a shorthand notation for lambda expressions that call a specific method.
 */
public class MethodReferences {
    
    /**
     * Demonstrates different types of method references.
     */
    public static void demonstrateMethodReferences() {
        // Static method reference
        Function<String, Integer> function = Integer::parseInt;
        Integer functionResult = function.apply("123");
        System.out.println("Static method reference result: " + functionResult);

        // Bounded method reference (instance method of a particular object)
        String str = "Hello World";
        Supplier<Integer> function1 = str::length;
        Integer function1Result = function1.get();
        System.out.println("Bounded method reference result: " + function1Result);

        // Unbounded method reference (instance method of an arbitrary object of a particular type)
        Function<String, String> function2 = String::toUpperCase;
        String function2Result = function2.apply(str);
        System.out.println("Unbounded method reference result: " + function2Result);

        // Constructor reference
        Supplier<String> function3 = String::new;
        String function3Result = function3.get();
        System.out.println("Constructor reference result: " + (function3Result.isEmpty() ? "empty string" : function3Result));

        // Method reference with Consumer
        Consumer<String> function4 = System.out::println;
        System.out.print("Consumer method reference result: ");
        function4.accept(str);
    }
}
