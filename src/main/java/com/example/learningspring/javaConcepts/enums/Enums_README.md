# Enums in Java

## Overview
An `enum` (enumeration) is a special Java type used to define a fixed set of named constants. Enums provide type safety, so a variable of an enum type can only hold one of the predefined values.

## Basic Syntax
```java
enum Color {
    RED,
    GREEN,
    BLUE;
}

public class Test {
    public static void main(String[] args) {
        Color c1 = Color.RED;
        System.out.println(c1);
    }
}
```

## What Happens Internally
An enum is internally desugared by the compiler into a final class with `public static final` instances — one per constant:
```java
/* internally, the enum Color above is converted to: */
class Color {
    public static final Color RED = new Color();
    public static final Color BLUE = new Color();
    public static final Color GREEN = new Color();
}
```
This is why enum constants can be compared with `==` and used in `switch` statements — they're singleton instances, not just int constants (unlike C/C++ enums).

## Usage Scenarios
- Representing a fixed set of related constants (days of week, states, status codes)
- Type-safe alternative to `int`/`String` constants
- Works naturally with `switch` statements (including enhanced switch)

## Additional Information
- Enums implicitly extend `java.lang.Enum` and can implement interfaces
- Enums can have fields, constructors, and methods
- Enums are commonly used with `EnumSet` and `EnumMap` for efficient collections
