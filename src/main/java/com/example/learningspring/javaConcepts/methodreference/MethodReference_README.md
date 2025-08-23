# Method References in Java

## Overview
Method references were introduced in Java 8 alongside lambda expressions. They provide a way to refer to methods or constructors without invoking them, offering an even more concise syntax than lambda expressions when the lambda simply calls an existing method.

## Key Features and Benefits
- **More concise syntax**: Even shorter than equivalent lambda expressions
- **Improved readability**: Clearly indicates which method is being used
- **Type inference**: The compiler determines the functional interface and parameter types
- **Reuse existing methods**: Leverages existing methods instead of rewriting them as lambdas
- **Works with functional interfaces**: Compatible with all functional interfaces

## Types of Method References
There are four kinds of method references in Java:

1. **Static method reference**: `ClassName::staticMethodName`
2. **Instance method reference of a particular object**: `instanceReference::methodName`
3. **Instance method reference of an arbitrary object of a particular type**: `ClassName::methodName`
4. **Constructor reference**: `ClassName::new`

## Example from Code
```java
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
```

## Different Types of Method References Demonstrated

### 1. Static Method Reference
```java
Function<String, Integer> function = Integer::parseInt;
```
- References a static method `parseInt` of the `Integer` class
- Equivalent lambda: `(String s) -> Integer.parseInt(s)`

### 2. Bounded Method Reference
```java
String str = "Hello World";
Supplier<Integer> function1 = str::length;
```
- References an instance method `length` of a specific object `str`
- Equivalent lambda: `() -> str.length()`

### 3. Unbounded Method Reference
```java
Function<String, String> function2 = String::toUpperCase;
```
- References an instance method `toUpperCase` of an arbitrary `String` object
- The first parameter of the functional interface becomes the receiver of the method
- Equivalent lambda: `(String s) -> s.toUpperCase()`

### 4. Constructor Reference
```java
Supplier<String> function3 = String::new;
```
- References a constructor of the `String` class
- Equivalent lambda: `() -> new String()`

## Comparison with Lambda Expressions
### Lambda Expression:
```java
Function<String, Integer> parseWithLambda = s -> Integer.parseInt(s);
```

### Method Reference:
```java
Function<String, Integer> parseWithReference = Integer::parseInt;
```

## Usage Scenarios
- **Stream operations**: Mapping, filtering, and reducing collections
- **Collection operations**: Sorting, iterating, and processing collections
- **Event handling**: Responding to UI events
- **Factory methods**: Creating instances of objects
- **Callbacks**: Implementing callback functions
- **Any scenario where a lambda would call a single method**

## Additional Information
- Method references were introduced in Java 8
- They work with functional interfaces (interfaces with a single abstract method)
- The compiler determines which overloaded method to use based on the context
- Method references can sometimes be more efficient than lambda expressions
- They can reference both instance and static methods
- They can reference methods with any number of parameters
