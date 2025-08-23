# Lambda Expressions in Java

## Overview
Lambda expressions were introduced in Java 8 as part of the Project Lambda. They provide a clear and concise way to represent one method interface using an expression. Lambda expressions are essentially anonymous methods (functions) that allow you to pass functionality as an argument to another method.

## Key Features and Benefits
- **Concise syntax**: Reduces boilerplate code compared to anonymous inner classes
- **Functional programming**: Enables functional programming paradigms in Java
- **Improved readability**: Makes code more readable and maintainable
- **Enables Stream API**: Powers the Stream API for processing collections
- **Method references**: Works with method references for even more concise code
- **Effectively final variables**: Can access effectively final variables from enclosing scope

## Lambda Syntax
The basic syntax of a lambda expression is:
```
(parameters) -> expression
```
or
```
(parameters) -> { statements; }
```

## Example from Code
```java
// Using lambda with forEach
List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
list1.forEach(element -> System.out.println(element));

// Using lambda with BinaryOperator
BinaryOperator<Integer> add = (x, y) -> x + y;
int result = add.apply(1, 2);
System.out.println("1 + 2 = " + result);

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
```

## Different Types of Lambda Expressions Demonstrated

### 1. Lambda with Single Expression
```java
element -> System.out.println(element)
```
- No need for curly braces
- No explicit return statement needed for expressions that return a value

### 2. Lambda with Parameters
```java
(x, y) -> x + y
```
- Parameter types are inferred from context
- Parentheses can be omitted for a single parameter with inferred type

### 3. Lambda with No Parameters
```java
() -> System.out.println("No parameters")
```
- Empty parentheses required when there are no parameters

### 4. Lambda with Multiple Statements
```java
(x, y) -> {
    System.out.println("Multiplying " + x + " and " + y);
    return x * y;
}
```
- Curly braces required for multiple statements
- Explicit return statement required if returning a value

## Comparison with Anonymous Inner Classes
### Before Lambda (Java 7 and earlier):
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.forEach(new Consumer<Integer>() {
    @Override
    public void accept(Integer number) {
        System.out.println(number);
    }
});
```

### With Lambda (Java 8+):
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.forEach(number -> System.out.println(number));
```

## Usage Scenarios
- **Event handling**: Responding to UI events
- **Collection iteration**: Processing elements in collections
- **Concurrent programming**: Simplifying thread creation and management
- **Callback mechanisms**: Implementing callback functions
- **Functional interfaces**: Implementing any functional interface
- **Stream operations**: Filtering, mapping, and reducing collections

## Additional Information
- Lambda expressions work with functional interfaces (interfaces with a single abstract method)
- Common functional interfaces are provided in the `java.util.function` package
- Lambda expressions can capture variables from their enclosing scope (must be effectively final)
- Method references (`Class::method`) can be used as an alternative to lambda expressions in many cases
- Lambda expressions are compiled into regular method calls, not anonymous classes
