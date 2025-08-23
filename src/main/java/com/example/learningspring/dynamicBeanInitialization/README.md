# Dynamic Bean Initialization in Spring

This package demonstrates how to dynamically initialize beans in Spring based on configuration properties, allowing for flexible application behavior without code changes.

## Key Concepts Demonstrated

### Dynamic Bean Creation
- **Configuration-based Bean Selection**: Creating different bean implementations based on property values
- **Runtime Bean Determination**: Deciding which implementation to use at application startup
- **Interface-based Design**: Using interfaces to abstract implementation details

### Implementation Examples
The package demonstrates dynamic bean initialization with an order processing system:
- **OrderDIB**: Interface defining the contract for order processing
- **OnlineOrderDIB**: Implementation for online orders
- **OfflineOrderDIB**: Implementation for offline orders
- **AppConfigDIB**: Configuration class that dynamically selects the appropriate implementation
- **TestControlleDIB**: Controller that uses the dynamically initialized bean

## How Dynamic Bean Initialization Works

In Spring, beans can be initialized dynamically based on various conditions:

```java
@Configuration
public class AppConfigDIB {
    @Bean
    public OrderDIB getOrderBean(@Value("${isOnlineOrder:false") String isOnlineOrder) {
        boolean isOnlineOrder1 = Boolean.parseBoolean(isOnlineOrder);
        if(isOnlineOrder1) {
            return new OnlineOrderDIB();
        }
        else {
            return new OfflineOrderDIB();
        }
    }
}
```

In this example:
1. The `@Configuration` annotation marks this as a Spring configuration class
2. The `@Bean` method creates an instance of `OrderDIB`
3. The `@Value` annotation injects the value of the `isOnlineOrder` property
4. Based on the property value, either `OnlineOrderDIB` or `OfflineOrderDIB` is instantiated
5. The method returns the appropriate implementation, which Spring registers as a bean

### Using the Dynamically Initialized Bean

Once the bean is initialized, it can be used like any other Spring bean:

```java
@RestController
public class TestControlleDIB {
    @Autowired
    OrderDIB orderDIB;

    @GetMapping("/testDIB")
    public String testDIB() {
        return orderDIB.createOrder();
    }
}
```

The controller doesn't need to know which implementation is being used; it simply works with the interface.

## Configuration Properties

The dynamic behavior is controlled by properties in the application configuration:

```properties
isOnlineOrder=false
```

With this configuration, the `OfflineOrderDIB` implementation will be used. Changing the property to `true` would switch to the `OnlineOrderDIB` implementation without any code changes.

## Techniques for Dynamic Bean Initialization

Spring provides several techniques for dynamic bean initialization:

1. **@Bean methods with conditional logic**: As demonstrated in this package
2. **@Conditional annotations**: Such as @ConditionalOnProperty, @ConditionalOnBean, etc.
3. **Factory beans**: Implementing FactoryBean interface for more complex initialization logic
4. **BeanFactoryPostProcessor**: For programmatic bean definition modification
5. **Profile-specific beans**: Using @Profile to create beans for specific environments

## Common Use Cases

1. **Feature Toggles**: Enable/disable features without code changes
2. **Environment-specific Implementations**: Different implementations for dev, test, prod
3. **A/B Testing**: Dynamically switch between different implementations
4. **Multi-tenant Applications**: Different implementations for different tenants
5. **Pluggable Components**: Allow for easy swapping of components
6. **Configuration-driven Behavior**: Adapt application behavior based on configuration

## Best Practices

1. **Use Interfaces**: Always code to interfaces, not implementations
2. **Provide Sensible Defaults**: Use default values for properties to ensure the application works even without explicit configuration
3. **Document Required Properties**: Clearly document all properties that affect bean initialization
4. **Keep Configuration Logic Simple**: Avoid complex conditional logic in @Bean methods
5. **Consider Using @Conditional Annotations**: For more complex conditions, use Spring's built-in conditional annotations
6. **Test Different Configurations**: Ensure your application works with all possible bean configurations
7. **Centralize Configuration**: Keep related configuration in one place for easier maintenance
8. **Use Meaningful Bean Names**: When multiple implementations exist, use clear naming conventions

## Related Resources
- [Spring @Bean Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts)
- [Spring @Value Annotation](https://www.baeldung.com/spring-value-annotation)
- [Conditional Bean Creation in Spring](https://www.baeldung.com/spring-conditional-annotations)
- [Spring Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
- [Factory Beans in Spring](https://www.baeldung.com/spring-factorybean)
