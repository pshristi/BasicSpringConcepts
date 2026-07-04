# Singleton Class

## Overview
A Singleton class restricts the number of object instances it allows to exactly one, and provides a single global point of access to that instance.

## Forms of Singleton
- **Early Instantiation**: The object is created at load time (eagerly, e.g. as a `static final` field initialized directly).
- **Lazy Instantiation**: The object is created only when it's actually needed (on first access).

## Example (Lazy Instantiation with `getInstance()`)
```java
class Singleton {
    // Static reference to the single instance
    private static Singleton single_instance = null;

    public String s;

    // Private constructor restricted to this class itself
    private Singleton() {
        s = "Hello I am a string part of Singleton class";
    }

    // Static method to create/return the instance
    public static Singleton getInstance() {
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }
}

class GFG {
    public static void main(String args[]) {
        Singleton x = Singleton.getInstance();
    }
}
```

## Key Points
- The constructor is `private` so no other class can instantiate it directly.
- `getInstance()` is the only way to obtain the instance.
- The lazy version above is **not thread-safe** as written — under concurrent access, two threads could both see `single_instance == null` and create two instances. Common fixes: synchronized `getInstance()`, double-checked locking, or an eagerly-initialized `static final` field (which the JVM guarantees is thread-safe).

## Usage Scenarios
- Configuration managers, logging, connection pools, caches — anywhere exactly one shared instance should exist application-wide.
