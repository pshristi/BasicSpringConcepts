# Inversion of Control (IoC) in Spring

This package demonstrates the Inversion of Control (IoC) principle in Spring Framework, which is a fundamental concept where the control of object creation and lifecycle is transferred to the Spring container.

## Key Concepts Demonstrated

### Bean Creation and Configuration
- **AppConfig.java**: Shows how to create beans programmatically using `@Configuration` and `@Bean` annotations
- **CorrectUser.java**: Demonstrates a properly configured bean with lifecycle hooks
- **WrongUser.java**: Shows limitations of using `@Component` with parameterized constructors

#### Creating Beans with @Configuration and @Bean
```java
// AppConfig.java
// @Configuration marks this class as a source of bean definitions
// Spring will process this class to generate bean definitions and service requests at runtime
@Configuration
public class AppConfig {
    // @Bean annotation tells Spring that this method will return an object
    // that should be registered as a bean in the Spring application context
    // The bean name will be the same as the method name by default (getCorrectUser1)
    @Bean
    public CorrectUser getCorrectUser1() {
        // We can configure the bean with constructor arguments
        // This gives us complete control over bean creation
        return new CorrectUser("Correct User 1");
    }

    // We can define multiple beans of the same type with different configurations
    // Each bean will have a unique name based on the method name
    @Bean
    public CorrectUser getCorrectUser2() {
        return new CorrectUser("Correct User 2");
    }
}
```

#### Limitations of @Component with Parameterized Constructors
```java
// WrongUser.java
// @Component is a class-level annotation that tells Spring to automatically detect and register this class as a bean
// Unlike @Bean which gives explicit control, @Component relies on component scanning and auto-configuration
@Component
public class WrongUser {
    String name;

    // Will not be able to use parameterized constructor with @Component annotation
    // Spring needs a default (no-arg) constructor to instantiate beans with @Component
    // If you need to pass parameters during bean creation, use @Configuration and @Bean instead
    /*
    public WrongUser(String name) {
        this.name = name;
    }
    */

    // Must use default constructor with @Component
    // Spring will call this constructor when creating the bean during component scanning
    // Any initialization must happen here or in @PostConstruct methods
    public WrongUser() {
        // With @Component, we're limited to hardcoded values or properties injection
        this.name = "Wrong User1";
        System.out.println("User created : " + name);
    }
}
```

### Bean Lifecycle
- **@PostConstruct and @PreDestroy**: Lifecycle hooks demonstrated in CorrectUser.java
- Bean initialization and destruction callbacks

#### Bean Lifecycle Hooks Example
```java
// CorrectUser.java
public class CorrectUser {
    String userName;

    // @PostConstruct annotation marks a method to be called after the bean has been constructed
    // and all its dependencies have been injected (autowired)
    // This is the ideal place to perform initialization logic that depends on injected resources
    // It runs only once in the bean's lifecycle, after construction but before the bean is put into service
    @PostConstruct
    public void init() {
        System.out.println("Correct user bean is created and all dependencies injected");
        // You can initialize resources, validate state, or start background processes here
    }

    // @PreDestroy annotation marks a method to be called just before the bean is destroyed
    // This happens when the application context is closed or the bean is explicitly removed
    // It's the ideal place to release resources and perform cleanup operations
    // For prototype-scoped beans, Spring does not call this method (a limitation to be aware of)
    @PreDestroy
    public void destroy() {
        System.out.println("Correct user bean is destroyed");
        // You can close connections, release resources, or save state here
    }

    // The constructor is called first in the bean lifecycle
    // Note that dependencies are not yet injected when the constructor executes
    // This is why @PostConstruct is important for initialization that requires dependencies
    public CorrectUser(String userName) {
        this.userName = userName;
        System.out.println("User created : " + userName);
        // Only perform basic initialization here that doesn't depend on other beans
    }
}
```

### Types of Dependency Injection
The `typesOfInjection` subpackage demonstrates different ways to inject dependencies:
- Constructor Injection (recommended approach)
- Setter Injection
- Field Injection (not recommended due to testing difficulties)

#### Constructor Injection
```java
// ConstructorInjection.java
public class ConstructorInjection {
    // Using 'final' ensures the dependency cannot be changed after initialization
    // This promotes immutability and thread safety
    final Order order;

    // Constructor injection is the recommended approach for required dependencies
    // Benefits:
    // 1. It is fail-fast - immediately fails if dependency is not available
    // 2. Promotes immutability - dependencies can be declared as final
    // 3. Makes unit testing easier - dependencies can be mocked in tests
    // 4. Makes dependencies explicit and visible
    // 5. Works with both Spring and non-Spring code (POJO friendly)

    // @Autowired is optional for constructor injection since Spring 4.3 if there's only one constructor
    // But it's good practice to include it for clarity
    @Autowired
    public ConstructorInjection(Order order) {
        this.order = order;
        // Constructor injection ensures all required dependencies are available when the bean is created
    }
}
```

#### Setter Injection
```java
// SetterInjection.java
// @Component marks this class for automatic detection and registration as a bean
@Component
public class SetterInjection {
    // Cannot create immutable object when setter injection is being used for Dependency Injection
    // final Order order; // This won't work with setter injection because final fields must be initialized in constructor
    Order order;

    // Setter injection is useful for:
    // 1. Optional dependencies - bean can still function without this dependency
    // 2. Dependencies that might change during runtime
    // 3. Circular dependency scenarios (though better to avoid circular dependencies)
    // 4. When you need to reconfigure a dependency after bean initialization

    // @Autowired on a setter method tells Spring to inject the dependency after construction
    // Unlike constructor injection, the bean is created first, then dependencies are injected
    @Autowired
    public void setOrder(Order order) {
        this.order = order;
        // You can perform additional logic when setting the dependency
        // For example, validation or initialization based on the dependency
    }
}
```

#### Field Injection
```java
// FieldInjection.java
public class FieldInjection {
    // Cannot create immutable object when "field injection" is being used for Dependency Injection
    // @Autowired
    // final Order order; // This won't work with field injection because final fields must be initialized at construction time

    // Field injection uses reflection to inject dependencies directly into fields
    // Drawbacks of field injection:
    // 1. Hides dependencies - dependencies are not visible in constructors or setters
    // 2. Makes testing harder - cannot easily inject mocks for testing
    // 3. Tightly couples the class to the Spring framework
    // 4. Cannot use with final fields (immutability not possible)
    // 5. No way to create a fully initialized object outside of Spring container
    @Autowired
    Order order;

    // With field injection, the constructor doesn't receive any dependencies
    // This makes it impossible to ensure the bean is fully initialized after construction
    public FieldInjection() {
        System.out.println("Field injection bean created");
        // At this point, 'order' is still null - it will be injected after construction
        // This can lead to NullPointerExceptions if you try to use the dependency here
    }

    // Spring team recommends avoiding field injection in favor of constructor or setter injection
}
```

### Lazy Initialization
The `lazyInitialization` subpackage shows:
- How to use `@Lazy` annotation to delay bean creation until it's needed
- Performance benefits of lazy initialization for expensive resources

#### Lazy Bean Creation
```java
// User1.java
public class User1 {
    // Since Payment bean is lazy, it will not be created until it is needed
    // i.e autowired here or called from another bean.
    // When a lazy bean is injected, Spring creates a proxy first and only initializes
    // the actual bean when a method is called on it
    @Autowired
    Payment payment;

    // When this method is called, the Payment bean will be fully initialized
    public void processPayment() {
        // This method call will trigger the actual creation of the Payment bean
        payment.process();
    }
}

// Payment.java (from lazyInitializationToAvoidCircularDependency)
// @Lazy annotation tells Spring to create this bean only when it's first requested
// rather than at application startup
// Benefits of lazy initialization:
// 1. Faster application startup time
// 2. Reduced memory consumption if the bean is never used
// 3. Helps resolve circular dependencies
// 4. Useful for beans that are expensive to create but rarely used
@Lazy
@Component
public class Payment {
    // This constructor won't be called until the bean is actually needed
    // This can significantly improve startup performance for expensive beans
    public Payment() {
        System.out.println("Payment created");
        // Expensive initialization would go here
    }

    public void process() {
        // Business logic
    }
}
```

### Resolving Circular Dependencies
The `lazyInitializationToAvoidCircularDependency` subpackage demonstrates:
- How circular dependencies can cause issues in Spring
- Using `@Lazy` to break circular dependency chains
- Best practices for avoiding circular dependencies

#### Breaking Circular Dependencies with @Lazy
```java
// Order.java
// A circular dependency occurs when bean A depends on bean B, and bean B depends on bean A
// This creates a chicken-and-egg problem for Spring during bean initialization
@Component
public class Order {
    // Problem: Order depends on Payment, but Payment depends on Order
    // Solution: Use @Lazy with @Autowired to break the circular dependency
    // How it works:
    // 1. Spring creates a proxy for the Payment bean instead of the actual bean
    // 2. The proxy is injected into Order
    // 3. The actual Payment bean is only created when a method is called on the proxy
    // 4. By that time, Order is fully initialized and can be safely injected into Payment
    @Lazy
    @Autowired
    Payment payment;

    public Order() {
        System.out.println("Order created");
        // At this point, payment is a proxy, not the actual bean
        // If you try to use payment here, the actual Payment bean will be created,
        // potentially causing the circular dependency issue again
    }

    // Better design would be to refactor to avoid circular dependencies entirely
    // For example, extract common functionality to a third service
}

// Payment.java
// This bean is marked as @Lazy, so it won't be created until needed
// This helps with circular dependencies because it delays the initialization
@Lazy
@Component
public class Payment {
    // This creates the circular dependency with Order
    // Without @Lazy on either this injection or the one in Order,
    // Spring would throw a BeanCurrentlyInCreationException
    @Autowired
    Order order;

    public Payment() {
        System.out.println("Payment created");
        // At this point, Order is already fully initialized
        // So it's safe to use order here
    }

    // Warning: Circular dependencies are generally a sign of poor design
    // Consider refactoring your code to eliminate them when possible
}
```

### Qualifier for Unsatisfied Dependencies
The `qualifierForUnsatisfiedDependency` subpackage shows:
- How to use `@Qualifier` annotation when multiple beans of the same type exist
- Resolving ambiguity in autowiring

#### Using @Qualifier and @Primary
```java
// Student.java
// This is a common interface that multiple implementations will implement
// When multiple implementations of an interface exist, Spring needs help deciding which one to inject
public interface Student {
    // Interface methods would go here
}

// OfflineStudent.java
// When multiple beans of the same type exist, Spring needs a way to determine which one to inject
// There are two main approaches:
// 1. Use @Primary to designate a "default" implementation
// 2. Use @Qualifier to provide a name that can be referenced when injecting
// Here we're using both approaches for flexibility

// @Primary marks this implementation as the default choice when no specific qualifier is provided
// Only one bean of a given type should be marked as @Primary
@Primary
@Component
// @Qualifier gives this bean a name that can be referenced when injecting
// This allows for more specific selection when needed
@Qualifier("offlineStudent")
public class OfflineStudent implements Student {
    // Implementation details
}

// OnlineStudent.java
// This implementation is not marked as @Primary, so it won't be the default choice
// It can only be injected when specifically requested using its qualifier
@Qualifier("onlineStudent")
@Component
public class OnlineStudent implements Student {
    // Implementation details
}

// School.java
public class School {
    String name;

    // If no Qualifier is provided, Spring will inject the @Primary bean
    // This approach is good for "default" dependencies where one implementation is used most often
    @Autowired
    Student student; // This will be OfflineStudent due to @Primary

    // When you need a specific implementation, use @Qualifier with the bean name
    // This allows you to have multiple implementations of the same interface injected into a single class
    @Autowired
    @Qualifier(value = "onlineStudent")
    Student onlineStudent; // This will specifically be OnlineStudent

    // You can also use qualifiers with constructor injection for required dependencies
    // This makes the dependencies more explicit and is generally preferred
    /*
    private final Student defaultStudent;
    private final Student onlineStudent;

    @Autowired
    public School(@Qualifier("offlineStudent") Student defaultStudent, 
                 @Qualifier("onlineStudent") Student onlineStudent) {
        this.defaultStudent = defaultStudent;
        this.onlineStudent = onlineStudent;
    }
    */
}
```

## Best Practices

1. **Prefer Constructor Injection**: It ensures all required dependencies are available at initialization time
2. **Avoid Field Injection**: It makes testing more difficult and hides dependencies
3. **Use @Lazy Sparingly**: Overuse can make application behavior less predictable
4. **Avoid Circular Dependencies**: They indicate design issues and can be resolved with better architecture
5. **Use Meaningful Bean Names**: When using qualifiers, choose descriptive names

## Related Resources
- [Spring IoC Container Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans)
- [Dependency Injection Patterns](https://www.baeldung.com/spring-dependency-injection)
