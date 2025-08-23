# Functional Interfaces in Java

## Overview
Functional interfaces were formalized in Java 8 as part of the lambda expressions feature. A functional interface is an interface that contains exactly one abstract method. These interfaces serve as the foundation for lambda expressions and method references in Java.

## Key Features and Benefits
- **Single Abstract Method**: Contains exactly one abstract method that needs to be implemented
- **@FunctionalInterface annotation**: Optional annotation that ensures the interface has only one abstract method
- **Lambda compatibility**: Can be implemented using lambda expressions or method references
- **Built-in interfaces**: Java provides many built-in functional interfaces in the `java.util.function` package
- **Default methods**: Can contain default methods with implementations
- **Static methods**: Can contain static methods
- **Composition**: Many functional interfaces support composition operations

## Common Functional Interfaces in Java
Java provides several built-in functional interfaces in the `java.util.function` package:

1. **Function<T, R>**: Takes an argument of type T and returns a result of type R
2. **Supplier<T>**: Takes no arguments and returns a result of type T
3. **Consumer<T>**: Takes an argument of type T and returns no result (void)
4. **Predicate<T>**: Takes an argument of type T and returns a boolean
5. **BiFunction<T, U, R>**: Takes arguments of types T and U and returns a result of type R
6. **BiConsumer<T, U>**: Takes arguments of types T and U and returns no result
7. **BiPredicate<T, U>**: Takes arguments of types T and U and returns a boolean
8. **UnaryOperator<T>**: Special case of Function where the input and output are of the same type
9. **BinaryOperator<T>**: Special case of BiFunction where all inputs and output are of the same type

## Example from Code
```java
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
```

## Different Types of Functional Interfaces Demonstrated

### 1. Function<T, R>
```java
Function<String, String> function1 = str::concat;
String result = function1.apply("World");
```
- Takes a String parameter and returns a String result
- Method to invoke: `apply(T t)`
- Used for transformations from one type to another

### 2. Supplier<T>
```java
Supplier<List<Integer>> function2 = ArrayList::new;
List<Integer> result1 = function2.get();
```
- Takes no parameters and returns a result
- Method to invoke: `get()`
- Used for lazy generation of values

### 3. Consumer<T>
```java
Consumer<String> function3 = System.out::println;
function3.accept(str);
```
- Takes a parameter and returns no result (void)
- Method to invoke: `accept(T t)`
- Used for operations that don't return a value

### 4. Predicate<T>
```java
Predicate<String> function4 = String::isEmpty;
Boolean result2 = function4.test(str);
```
- Takes a parameter and returns a boolean
- Method to invoke: `test(T t)`
- Used for filtering and testing conditions

## Function Composition
The example demonstrates function composition with:

### Function Chaining
```java
Function<Integer, Integer> multiplyThenAdd = multiply.andThen(add);
```
- `andThen()` creates a new function that applies the second function to the result of the first
- There's also `compose()` which applies functions in the reverse order

### Predicate Combination
```java
Predicate<String> combined = hasLength.and(containsWorld);
```
- `and()` creates a new predicate that is true only if both predicates are true
- `or()` creates a new predicate that is true if either predicate is true
- `negate()` creates a new predicate that is the logical negation of the original

## Usage Scenarios
- **Data processing**: Transforming, filtering, and validating data
- **Event handling**: Responding to events in a functional way
- **Callbacks**: Implementing callback functions
- **Stream operations**: Powering the Stream API
- **Dependency injection**: Providing behavior as a parameter
- **Strategy pattern**: Implementing different strategies as functional interfaces

## Additional Information
- Functional interfaces were formalized in Java 8
- The `@FunctionalInterface` annotation is optional but recommended
- Functional interfaces can have any number of default or static methods
- They can also override methods from Object class without affecting their "functional" status
- Many existing interfaces from earlier Java versions were retrofitted as functional interfaces (e.g., Runnable, Callable)
- Custom functional interfaces can be created for specific needs
