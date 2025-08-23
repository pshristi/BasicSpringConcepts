# Java Streams

## Overview
Java Streams were introduced in Java 8 as part of the functional programming enhancements. A Stream is a sequence of elements that supports sequential and parallel aggregate operations. Streams provide a high-level abstraction for processing data in a declarative way, similar to SQL queries.

## Key Features and Benefits
- **Declarative style**: Focus on what to do, not how to do it
- **Pipeline operations**: Chain multiple operations together
- **Lazy evaluation**: Operations are only executed when needed
- **Parallel processing**: Easy parallelization without explicit thread management
- **No storage**: Streams don't store elements; they're computed on demand
- **Functional approach**: Based on functional programming concepts
- **Reduced boilerplate**: Less code compared to loops and iterators

## Stream Operations
Stream operations are divided into two categories:

1. **Intermediate operations**: Return a new stream and are lazy (e.g., filter, map, sorted)
2. **Terminal operations**: Produce a result or side-effect and are eager (e.g., forEach, collect, reduce)

## Example from Code
```java
// Concat streams
System.out.println("Concatenating streams:");
var stream1 = Stream.of("a", "b", "c")
        .map(String::toUpperCase);
var stream2 = Arrays.stream(new String[]{"d", "e", "f"})
        .map(a -> a + "-");
Stream.concat(stream1, stream2)
        .filter(a -> a.length() > 1)
        .forEach(System.out::println);

// Generate infinite stream with random numbers
System.out.println("\nGenerating infinite stream with random numbers (limited to 5):");
Random random = new Random();
Stream.generate(random::nextInt)
        .limit(5)
        .forEach(System.out::println);

// Generate infinite stream with sequential numbers
System.out.println("\nGenerating infinite stream with sequential numbers (limited to 5):");
Stream.iterate(0, n -> n + 1)
        .limit(5)
        .forEach(System.out::println);

// Additional stream operations
System.out.println("\nAdditional stream operations:");

// Filter and map
System.out.println("Filter and map:");
Stream.of(1, 2, 3, 4, 5, 6)
        .filter(n -> n % 2 == 0)
        .map(n -> n * n)
        .forEach(System.out::println);

// Reduce
System.out.println("\nReduce operation:");
int sum = Stream.of(1, 2, 3, 4, 5)
        .reduce(0, Integer::sum);
System.out.println("Sum of 1 to 5: " + sum);
```

## Different Stream Operations Demonstrated

### 1. Creating Streams
```java
// From explicit values
Stream.of("a", "b", "c")

// From arrays
Arrays.stream(new String[]{"d", "e", "f"})

// From a generator function (infinite)
Stream.generate(random::nextInt)

// From an iterator function (infinite)
Stream.iterate(0, n -> n + 1)
```

### 2. Intermediate Operations
```java
// Map: Transform elements
.map(String::toUpperCase)
.map(a -> a + "-")
.map(n -> n * n)

// Filter: Select elements based on a predicate
.filter(a -> a.length() > 1)
.filter(n -> n % 2 == 0)

// Limit: Truncate stream to specified size
.limit(5)
```

### 3. Terminal Operations
```java
// ForEach: Perform an action for each element
.forEach(System.out::println)

// Reduce: Combine elements to produce a single result
.reduce(0, Integer::sum)
```

### 4. Stream Composition
```java
// Concatenate two streams
Stream.concat(stream1, stream2)
```

## Comparison with Traditional Approach
### Before Streams (Java 7 and earlier):
```java
// Calculate sum of even squares
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
int sum = 0;
for (Integer n : numbers) {
    if (n % 2 == 0) {
        int square = n * n;
        sum += square;
    }
}
System.out.println(sum);
```

### With Streams (Java 8+):
```java
// Calculate sum of even squares
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
int sum = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .reduce(0, Integer::sum);
System.out.println(sum);
```

## Usage Scenarios
- **Data processing**: Filtering, mapping, and reducing collections
- **File processing**: Processing lines from files
- **Database queries**: Processing query results
- **Parallel computing**: Leveraging multi-core processors
- **Big data**: Processing large datasets efficiently
- **Functional programming**: Implementing functional programming patterns

## Additional Information
- Streams were introduced in Java 8
- They work well with lambda expressions and method references
- Streams can be sequential or parallel
- Parallel streams use the Fork/Join framework internally
- Streams are not reusable; once a terminal operation is performed, the stream is consumed
- Common collectors (Collectors class) can be used to collect stream results into collections
- Specialized streams exist for primitives (IntStream, LongStream, DoubleStream)
