# Conditional Bean Creation with @ConditionalOnProperty in Spring

This package demonstrates how to use the `@ConditionalOnProperty` annotation in Spring to conditionally create beans based on property values in the application configuration.

## Key Concepts Demonstrated

### Conditional Bean Creation
- **@ConditionalOnProperty**: Conditionally create beans based on property values
- **Property-based Configuration**: Control application behavior through external configuration
- **Conditional Autowiring**: Handle optional dependencies with `required=false`

### Implementation Examples
The package demonstrates conditional bean creation with two database connection classes:
- **SqlDBConnection**: Created only when `sql.enabled=true`
- **NoSqlDBConnection**: Created only when `nosql.enabled=true`
- **TestCOPController**: Shows how to work with conditionally created beans

## How @ConditionalOnProperty Works

The `@ConditionalOnProperty` annotation allows Spring to conditionally register beans based on property values:

```java
@Component
@ConditionalOnProperty(
    prefix = "sql", 
    value = "enabled", 
    havingValue = "true", 
    matchIfMissing = false
)
public class SqlDBConnection {
}
```

### Parameters Explained
- **prefix**: The prefix of the property to check (e.g., "sql" for "sql.enabled")
- **value**: The name of the property without the prefix (e.g., "enabled")
- **havingValue**: The required value for the condition to be true
- **matchIfMissing**: Whether to match if the property is missing (default is false)
- **name**: Alternative to prefix+value, specifies the full property name

### Working with Conditional Beans
When using conditional beans, you need to handle the case where they might not exist:

```java
@RestController
public class TestCOPController {
    @Autowired(required = false)
    SqlDBConnection sqlDBConnection;

    @Autowired(required = false)
    NoSqlDBConnection noSqlDBConnection;

    @GetMapping("/test-conditional-on-property")
    public void testConditionalOnProperty() {
        System.out.println(Objects.nonNull(sqlDBConnection)? "sql is enabled" : "");
        System.out.println(Objects.nonNull(noSqlDBConnection)? "nosql is enabled" : "");
    }
}
```

## Property Configuration

The conditional beans in this package are controlled by properties in the application configuration:

### Main Properties (application.properties)
```properties
sql.enabled=true
spring.profiles.active=qa
```

### Profile-specific Properties (application-qa.properties)
```properties
nosql.enabled=true
```

With this configuration, both `SqlDBConnection` and `NoSqlDBConnection` beans would be created when the application runs with the "qa" profile active.

## Other Conditional Annotations in Spring

Spring provides several other conditional annotations for different use cases:

- **@ConditionalOnBean**: Condition based on the presence of a specific bean
- **@ConditionalOnMissingBean**: Condition based on the absence of a specific bean
- **@ConditionalOnClass**: Condition based on the presence of a specific class
- **@ConditionalOnMissingClass**: Condition based on the absence of a specific class
- **@ConditionalOnExpression**: Condition based on a SpEL expression
- **@ConditionalOnJava**: Condition based on the Java version
- **@ConditionalOnResource**: Condition based on the presence of a resource
- **@ConditionalOnWebApplication**: Condition based on whether it's a web application
- **@ConditionalOnNotWebApplication**: Condition based on whether it's not a web application
- **@ConditionalOnCloudPlatform**: Condition based on the cloud platform

## Common Use Cases

1. **Feature Toggles**: Enable/disable features without code changes
2. **Environment-specific Beans**: Different implementations for dev, test, prod
3. **Optional Dependencies**: Conditionally use libraries if they're available
4. **Multiple Implementations**: Choose between different implementations based on configuration
5. **Gradual Rollout**: Enable features for specific users or environments

## Best Practices

1. **Use Meaningful Property Names**: Choose clear, descriptive property names
2. **Document Required Properties**: Document all properties that affect bean creation
3. **Provide Sensible Defaults**: Use `matchIfMissing` appropriately to provide defaults
4. **Handle Missing Beans**: Use `@Autowired(required=false)` or `Optional<>` for conditional beans
5. **Group Related Properties**: Use common prefixes for related properties
6. **Test Different Configurations**: Ensure your application works with different property combinations
7. **Consider Profiles**: For complex scenarios, Spring profiles might be more appropriate
8. **Avoid Overuse**: Don't make everything conditional; focus on genuine configuration points

## Related Resources
- [Spring Conditional Annotations Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration.condition-annotations)
- [Guide to @ConditionalOnProperty](https://www.baeldung.com/spring-conditionalonproperty)
- [Spring Boot Features: Developing Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
