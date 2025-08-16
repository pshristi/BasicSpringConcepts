# Inversion of Control (IoC) in Spring

This package demonstrates the Inversion of Control (IoC) principle in Spring Framework, which is a fundamental concept where the control of object creation and lifecycle is transferred to the Spring container.

## Key Concepts Demonstrated

### Bean Creation and Configuration
- **AppConfig.java**: Shows how to create beans programmatically using `@Configuration` and `@Bean` annotations
- **CorrectUser.java**: Demonstrates a properly configured bean with lifecycle hooks
- **WrongUser.java**: Shows limitations of using `@Component` with parameterized constructors

### Bean Lifecycle
- **@PostConstruct and @PreDestroy**: Lifecycle hooks demonstrated in CorrectUser.java
- Bean initialization and destruction callbacks

### Types of Dependency Injection
The `typesOfInjection` subpackage demonstrates different ways to inject dependencies:
- Constructor Injection (recommended approach)
- Setter Injection
- Field Injection (not recommended due to testing difficulties)

### Lazy Initialization
The `lazyInitialization` subpackage shows:
- How to use `@Lazy` annotation to delay bean creation until it's needed
- Performance benefits of lazy initialization for expensive resources

### Resolving Circular Dependencies
The `lazyInitializationToAvoidCircularDependency` subpackage demonstrates:
- How circular dependencies can cause issues in Spring
- Using `@Lazy` to break circular dependency chains
- Best practices for avoiding circular dependencies

### Qualifier for Unsatisfied Dependencies
The `qualifierForUnsatisfiedDependency` subpackage shows:
- How to use `@Qualifier` annotation when multiple beans of the same type exist
- Resolving ambiguity in autowiring

## Best Practices

1. **Prefer Constructor Injection**: It ensures all required dependencies are available at initialization time
2. **Avoid Field Injection**: It makes testing more difficult and hides dependencies
3. **Use @Lazy Sparingly**: Overuse can make application behavior less predictable
4. **Avoid Circular Dependencies**: They indicate design issues and can be resolved with better architecture
5. **Use Meaningful Bean Names**: When using qualifiers, choose descriptive names

## Code Examples

### Creating Beans with @Bean
```java
@Configuration
public class AppConfig {
    @Bean
    public CorrectUser getCorrectUser1() {
        return new CorrectUser("Correct User 1");
    }
}
```

### Bean Lifecycle Hooks
```java
@PostConstruct
public void init() {
    System.out.println("Bean is created and all dependencies injected");
}

@PreDestroy
public void destroy() {
    System.out.println("Bean is destroyed");
}
```

## Related Resources
- [Spring IoC Container Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans)
- [Dependency Injection Patterns](https://www.baeldung.com/spring-dependency-injection)
