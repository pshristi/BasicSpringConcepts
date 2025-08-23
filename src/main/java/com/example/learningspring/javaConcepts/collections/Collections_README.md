# Java Collections

## Overview
The Java Collections Framework provides a unified architecture for representing and manipulating collections of objects. It contains interfaces, implementations, and algorithms that allow developers to work with groups of objects efficiently.

## Key Features and Benefits
- **Unified Architecture**: Common interfaces and base classes for all collections
- **Reduced Programming Effort**: Ready-to-use data structures and algorithms
- **Increased Program Speed and Quality**: High-performance implementations of useful data structures and algorithms
- **Interoperability**: Common language for passing collections between APIs
- **Type Safety**: Generic types ensure compile-time type checking
- **Rich Functionality**: Sorting, searching, insertion, deletion, and more

## Collection Hierarchy
- **Collection**: The root interface in the collection hierarchy
  - **List**: An ordered collection (sequence)
    - ArrayList, LinkedList, Vector, Stack
  - **Set**: A collection that cannot contain duplicate elements
    - HashSet, LinkedHashSet, TreeSet
  - **Queue**: A collection designed for holding elements prior to processing
    - PriorityQueue, LinkedList
  - **Deque**: A double-ended queue
    - ArrayDeque, LinkedList
- **Map**: An object that maps keys to values
  - HashMap, LinkedHashMap, TreeMap, Hashtable

## Example from Code
```java
// Creating arrays and different types of lists
String[] str = {"1", "2"};

// Immutable list
List<String> immutableList = List.of(str);
System.out.println("Immutable list: " + immutableList);

// Mutable ArrayList
ArrayList<String> mutableList = new ArrayList<>(List.of("1", "2", "3", ""));
System.out.println("Mutable list: " + mutableList);

// Non-resizable but mutable list
List<String> nonresizeablebutmutableList1 = Arrays.asList(str);
System.out.println("Non-resizable but mutable list: " + nonresizeablebutmutableList1);

// Another mutable ArrayList
ArrayList<String> mutableList1 = new ArrayList<>(Arrays.asList("1", "2", "3", ""));
System.out.println("Another mutable list: " + mutableList1);

// Converting list to array
String[] str1 = mutableList.toArray(new String[mutableList.size()]);
System.out.println("Array from list (explicit size): " + Arrays.toString(str1));

// More efficient way to convert list to array
var str2 = mutableList1.toArray(new String[0]);
System.out.println("Array from list (size 0): " + Arrays.toString(str2));
```

## Different Types of Lists Demonstrated

### 1. Immutable List (List.of)
- Created using `List.of()`
- Cannot be modified after creation
- Attempts to modify will throw UnsupportedOperationException
- Available since Java 9

### 2. Mutable ArrayList
- Created using `new ArrayList<>()`
- Can be modified (add, remove, clear elements)
- Dynamically resizes as elements are added
- Fast random access, but slower insertions/deletions

### 3. Fixed-size List (Arrays.asList)
- Created using `Arrays.asList()`
- Elements can be modified, but the size is fixed
- Cannot add or remove elements
- Backed by the original array

## Converting Between Lists and Arrays
The example shows two ways to convert a List to an Array:

1. Using explicit size: `list.toArray(new String[list.size()])`
2. Using zero-length array (more efficient): `list.toArray(new String[0])`

## Usage Scenarios
- **Lists**: When order matters and duplicates are allowed
  - Maintaining a sequence of elements
  - Accessing elements by index
  - Implementing stacks and queues
- **Sets**: When uniqueness matters
  - Removing duplicates from a collection
  - Membership testing
- **Maps**: When key-value associations are needed
  - Dictionaries
  - Caches
  - Lookup tables

## Additional Information
- Collections introduced in Java 1.2
- Enhanced with generics in Java 5
- Further improved with factory methods in Java 9
- Thread-safe collections available in java.util.concurrent package
- Unmodifiable and synchronized wrappers available through Collections utility class
