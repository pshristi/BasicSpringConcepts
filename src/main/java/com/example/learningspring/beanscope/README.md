# Bean Scopes in Spring

This package demonstrates different bean scopes in Spring Framework, which control the lifecycle and visibility of Spring beans.

## Key Concepts Demonstrated

### Bean Scope Types
- **Singleton Scope**: The default scope where only one instance of a bean is created per Spring container
- **Prototype Scope**: A new instance is created each time the bean is requested
- **Request Scope**: A new instance is created for each HTTP request (web applications only)

### Scope Interactions
The package demonstrates important interactions between beans of different scopes:
- Singleton beans with prototype dependencies
- Singleton beans with request-scoped dependencies
- Proxy creation for shorter-lived scopes

## Bean Scope Implementations

### Singleton Scope
The `StudentPrototypeScope` class demonstrates the singleton scope:
```java
@Component
@Scope("singleton")  // or simply @Component without @Scope
public class StudentPrototypeScope {
    @Autowired
    private UserPrototypeScope user;
    
    @PostConstruct
    public void init() {
        System.out.println("Student created with hashcode: " + this.hashCode());
        System.out.println("User created with hashcode: " + user.hashCode());
    }
}
```

Key points:
- Only one instance exists throughout the application
- The same instance is shared by all beans that reference it
- It's the default scope if none is specified

### Prototype Scope
The `UserPrototypeScope` class demonstrates the prototype scope:
```java
@Component
@Scope(value = "prototype")
public class UserPrototypeScope {
    @PostConstruct
    public void init() {
        System.out.println("User initialized with hashcode: " + this.hashCode());
    }
}
```

Key points:
- A new instance is created each time the bean is requested
- Not shared between different beans that reference it
- Spring does not manage the complete lifecycle (no destruction callbacks)

### Request Scope
The `UserRequestScope` class demonstrates the request scope:
```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRequestScope {
    @PostConstruct
    public void init() {
        System.out.println("User initialized with hashcode: " + this.hashCode());
    }
}
```

Key points:
- A new instance is created for each HTTP request
- Requires `proxyMode` to be specified when injected into singleton beans
- Only available in web applications

## Important Scope Interactions

### Singleton with Prototype Dependencies
When a singleton bean (like `StudentPrototypeScope` or `TestPrototypeScopeController`) has a dependency on a prototype bean (`UserPrototypeScope`), the prototype bean is only injected once when the singleton is created, not each time the prototype bean is requested.

```java
@RestController
public class TestPrototypeScopeController {
    @Autowired
    private UserPrototypeScope user;  // Same instance used throughout controller's lifecycle
    
    // ...
}
```

To get a new instance of a prototype bean each time, you can:
1. Use ApplicationContext.getBean() method
2. Use method injection with @Lookup annotation
3. Use scoped proxies
4. Use ObjectFactory or Provider injection

### Singleton with Request-Scoped Dependencies
When a singleton bean (like `TestRequestScopeController`) has a dependency on a request-scoped bean (`UserRequestScope`), Spring injects a proxy that delegates to the actual request-scoped bean for the current request.

```java
@RestController
@Scope("singleton")
public class TestRequestScopeController {
    @Autowired
    UserRequestScope user;  // This is actually a proxy
    
    // ...
}
```

The comment in the code explains this: "Using scope proxy on user, we do not really create new user instance but just use proxy for this bean creation"

## Other Available Scopes

In addition to the scopes demonstrated in this package, Spring also supports:

- **Session Scope**: One instance per user session
- **Application Scope**: One instance per ServletContext (similar to singleton but for web applications)
- **WebSocket Scope**: One instance per WebSocket session
- **Custom Scopes**: Developers can define their own custom scopes

## Best Practices

1. **Use Singleton by Default**: For most beans, singleton scope is appropriate and efficient
2. **Be Careful with Stateful Beans**: Prototype scope is better for beans that maintain state
3. **Consider Thread Safety**: Singleton beans should be thread-safe if they maintain state
4. **Use Proxies for Web Scopes**: Always use proxyMode when injecting request/session scoped beans into singletons
5. **Be Aware of Proxy Limitations**: Proxies have limitations with final classes, final methods, and self-invocation
6. **Consider Memory Usage**: Prototype and request scopes create more objects, which affects memory usage

## Related Resources
- [Spring Bean Scopes Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes)
- [Understanding Bean Scopes in Spring](https://www.baeldung.com/spring-bean-scopes)
- [Proxies in Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection)
