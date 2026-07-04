# Immutable Class

## Overview
An immutable class is one whose object's content cannot change once created. Examples in the JDK: wrapper classes (`Integer`, `Long`, etc.) and `String`.

## Rules to Create an Immutable Class
1. Declare the class as `final` so it can't be subclassed.
2. Declare all data members `private` so direct access isn't allowed.
3. Declare all data members `final` so their value can't change after object creation.
4. The parameterized constructor should initialize all fields, performing a **deep copy** so the caller can't mutate internal state through their original reference.
5. Getter methods should perform a **deep copy** when returning mutable fields, returning a copy rather than the actual object reference.
6. There should be **no setters**.

## Usage Scenarios
- Thread safety without synchronization (immutable objects are inherently thread-safe, since no thread can change their state).
- Safe to use as `HashMap`/`HashSet` keys (hash code never changes).
- Value objects: dates, money amounts, coordinates, configuration values.

## Related Concepts
- Combines well with [Records](../../records/Records_README.md), which give a concise, immutable-by-default alternative to hand-written immutable classes for simple data carriers.
