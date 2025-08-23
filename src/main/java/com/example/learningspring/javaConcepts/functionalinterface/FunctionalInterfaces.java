package com.example.learningspring.javaConcepts.functionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class demonstrates the use of common Functional Interfaces in Java.
 * Functional interfaces are interfaces that have a single abstract method.
 */
public class FunctionalInterfaces {
    
    /**
     * Demonstrates different types of functional interfaces.
     */
    public static void demonstrateFunctionalInterfaces() {
        String str = "Hello";
        
        // Function - takes one argument and produces a result
        System.out.println("Function example:");
        Function<String, String> function1 = str::concat;
        String result = function1.apply("World");
        System.out.println("Function result (concat): " + result);
        
        // Supplier - provides a result without taking any input
        System.out.println("\nSupplier example:");
        Supplier<List<Integer>> function2 = ArrayList::new;
        List<Integer> result1 = function2.get();
        result1.add(1);
        result1.add(2);
        System.out.println("Supplier result (new ArrayList): " + result1);
        
        // Consumer - accepts a single input and returns no result
        System.out.println("\nConsumer example:");
        Consumer<String> function3 = System.out::println;
        System.out.print("Consumer result (println): ");
        function3.accept(str);
        
        // Predicate - represents a boolean-valued function of one argument
        System.out.println("\nPredicate example:");
        Predicate<String> function4 = String::isEmpty;
        Boolean result2 = function4.test(str);
        System.out.println("Predicate result (isEmpty): " + result2);
        
        // Additional examples
        System.out.println("\nAdditional examples:");
        
        // Function with andThen
        Function<Integer, Integer> multiply = x -> x * 2;
        Function<Integer, Integer> add = x -> x + 3;
        Function<Integer, Integer> multiplyThenAdd = multiply.andThen(add);
        System.out.println("Function andThen: 5 * 2 + 3 = " + multiplyThenAdd.apply(5));
        
        // Predicate with and/or
        Predicate<String> hasLength = s -> s.length() > 0;
        Predicate<String> containsWorld = s -> s.contains("World");
        System.out.println("Predicate AND: " + hasLength.and(containsWorld).test(result));
        System.out.println("Predicate OR: " + hasLength.or(containsWorld).test(str));
    }
}
