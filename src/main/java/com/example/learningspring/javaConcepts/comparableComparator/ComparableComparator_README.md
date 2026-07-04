# Comparable and Comparator

## Overview
Java provides two interfaces for ordering objects of a user-defined class: `Comparable` and `Comparator`.

## Comparable Interface
- A comparable object is capable of comparing itself with another object.
- The class itself must implement `java.lang.Comparable` and override `compareTo()`.
- To sort, make an instance of the comparable class and call the sort method over it (e.g. `Collections.sort(list)`).
- Syntax: `public int compareTo(Object obj)` — returns negative, zero, or positive if this object is less than, equal to, or greater than the argument.

## Comparator Interface
- An external, separate class must implement `Comparator` and override `compare()`.
- To sort, make an instance of the `Comparator` class and call the overloaded sort method, passing both the list and the comparator instance:
  `public void sort(List list, ComparatorClass c)`
- Syntax: `public int compare(Object obj1, Object obj2)` — returns -1, 0, or 1 to say if `obj1` is less than, equal to, or greater than `obj2`.

## When to Use Which
- **Comparable**: When there's one natural/default ordering for the class (e.g., sorting `Integer`s numerically).
- **Comparator**: When you need multiple different orderings, or you can't modify the class whose objects you're sorting.

## Additional Information
- Related to the [Java Collections](../collections/Collections_README.md) framework — both are commonly used with `Collections.sort()` and `List.sort()`.
- `Comparable` is also an example of a built-in functional/single-method interface — see [Functional Interfaces](../functionalinterface/FunctionalInterface_README.md).
