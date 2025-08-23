package com.example.learningspring.javaConcepts.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * This class demonstrates the use of Lambda expressions in Java.
 */
public class LambdaExpressions {
    
    /**
     * Demonstrates different ways to use lambda expressions.
     */
    public static void demonstrateLambdaExpressions() {
        // Using lambda with forEach
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Using lambda with forEach:");
        list1.forEach(element -> System.out.println(element));
        
        // Using lambda with BinaryOperator
        BinaryOperator<Integer> add = (x, y) -> x + y;
        int result = add.apply(1, 2);
        System.out.println("\nUsing lambda with BinaryOperator:");
        System.out.println("1 + 2 = " + result);
        
        // More examples of lambda expressions
        System.out.println("\nMore lambda examples:");
        
        // Lambda with no parameters
        Runnable noParams = () -> System.out.println("No parameters");
        noParams.run();
        
        // Lambda with one parameter (type inferred)
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("Hello, " + name));
        
        // Lambda with multiple parameters and multiple statements
        BinaryOperator<Integer> multiply = (x, y) -> {
            System.out.println("Multiplying " + x + " and " + y);
            return x * y;
        };
        System.out.println("3 * 4 = " + multiply.apply(3, 4));
    }
}
