# Enhanced Switch in Java

## Overview
The Enhanced Switch feature was introduced in Java 12 and further improved in Java 14. It provides a more concise and safer way to express switch statements and expressions in Java.

## Key Features and Benefits
- **Multiple case labels**: You can group multiple case labels together, separated by commas
- **Arrow syntax**: Use `->` instead of colons and break statements
- **Expression form**: Switch can now be used as an expression that returns a value
- **Exhaustiveness checking**: The compiler ensures all possible cases are handled
- **No fall-through**: Each case is treated as a separate branch, eliminating the need for break statements
- **Yield statement**: Use `yield` to return values from complex switch expressions with blocks

## Example from Code
```java
public static String getQuarter(String month) {
    return switch (month) {
        case "January", "February", "March" -> {yield "1st";}
        case "April", "May", "June" -> "2nd";
        case "July", "August", "September" -> "3rd";
        case "October", "November", "December" -> "4th";
        default -> "Invalid month";
    };
}
```

In this example:
- Multiple months are grouped in each case using commas
- The arrow syntax (`->`) is used instead of colons and break statements
- The switch is used as an expression that returns a value
- The `yield` keyword is used in a block to return a value
- A default case ensures all possible inputs are handled

## Comparison with Traditional Switch
### Traditional Switch:
```java
public static String getQuarterOld(String month) {
    String quarter;
    switch (month) {
        case "January":
        case "February":
        case "March":
            quarter = "1st";
            break;
        case "April":
        case "May":
        case "June":
            quarter = "2nd";
            break;
        case "July":
        case "August":
        case "September":
            quarter = "3rd";
            break;
        case "October":
        case "November":
        case "December":
            quarter = "4th";
            break;
        default:
            quarter = "Invalid month";
            break;
    }
    return quarter;
}
```

### Enhanced Switch:
```java
public static String getQuarter(String month) {
    return switch (month) {
        case "January", "February", "March" -> "1st";
        case "April", "May", "June" -> "2nd";
        case "July", "August", "September" -> "3rd";
        case "October", "November", "December" -> "4th";
        default -> "Invalid month";
    };
}
```

## Usage Scenarios
- Pattern matching (especially with Java 17+ pattern matching for switch)
- Mapping enum values to different results
- Converting string inputs to specific outputs
- Any scenario where you need to select from multiple options based on a value

## Additional Information
- The enhanced switch was introduced as a preview feature in Java 12
- It became a standard feature in Java 14
- Pattern matching for switch (Java 17+) further extends this feature
