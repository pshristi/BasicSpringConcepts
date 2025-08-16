# Interceptors and Filters in Spring

This package demonstrates the implementation and usage of interceptors and filters in Spring Framework, which are powerful mechanisms for processing requests and responses at different points in the request handling lifecycle.

## Key Concepts Demonstrated

### Servlet Filters
The `customFilters` subpackage demonstrates:
- Creating custom filters by implementing the `jakarta.servlet.Filter` interface
- Registering filters with specific URL patterns and execution order
- Filter lifecycle methods: init(), doFilter(), and destroy()
- Filter chaining and execution order

### Spring Interceptors
The `customInterceptor` subpackage demonstrates:
- Creating custom interceptors by implementing the `HandlerInterceptor` interface
- Registering interceptors with specific path patterns and exclusions
- Interceptor lifecycle methods: preHandle(), postHandle(), and afterCompletion()
- Interceptor execution order

### Custom Annotations with Interceptors
The `customAnnotation` subpackage demonstrates:
- Creating custom annotations with various attribute types
- Implementing aspect-based interceptors that target methods with specific annotations
- Accessing annotation attributes at runtime
- Using custom annotations to control method behavior

## Filters vs Interceptors: Key Differences

### Filters
- Part of the Servlet API (not Spring-specific)
- Execute before the DispatcherServlet processes the request
- Can modify both the request and response objects
- Can prevent the request from reaching the DispatcherServlet
- Applied to all requests matching URL patterns, regardless of which controller handles them
- Ideal for cross-cutting concerns like authentication, logging, CORS, etc.

### Interceptors
- Part of the Spring MVC framework
- Execute after the DispatcherServlet receives the request but before the controller is called
- Cannot modify the request and response objects but can add attributes
- Can prevent the handler (controller) from being executed
- More integrated with Spring's request processing lifecycle
- Access to Spring's handler execution chain and model-view objects

## Code Examples

### Custom Filter Implementation
```java
public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Inside MyFilter1.doFilter() Starts");
        chain.doFilter(request, response); // continue processing the request and response chain
        System.out.println("Inside MyFilter1.doFilter() Completed");
    }
}
```

### Filter Registration
```java
@Configuration
public class AppConfigForFilter {
    @Bean
    public FilterRegistrationBean<MyFilter1> getMyFilter1() {
        FilterRegistrationBean<MyFilter1> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter1());
        registrationBean.addUrlPatterns("/*"); // URL patterns to apply the filter
        registrationBean.setOrder(2); // Order of the filter in the filter chain
        return registrationBean;
    }
}
```

### Custom Interceptor Implementation
```java
public class MyCustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Inside MyCustomInterceptor.preHandle()");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Inside MyCustomInterceptor.postHandle()");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       System.out.println("Inside MyCustomInterceptor.afterCompletion()");
    }
}
```

### Interceptor Registration
```java
@Configuration
public class AppConfigForInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyCustomInterceptor())
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/exclude");
    }
}
```

### Custom Annotation
```java
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCustomAnnotation {
    int intKey() default 0;
    String stringKey() default "defaultKey";
    Class<?> classKey() default Object.class;
    MyCustomEnum enumKey() default MyCustomEnum.VALUE1;
}
```

### Annotation-based Interceptor
```java
@Component
@Aspect
public class MyCustomAnnotationInterceptor {
    @Around("@annotation(com.example.learningspring.interceptorsAndFilters.customAnnotation.MyCustomAnnotation.class)")
    public void invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if(method.isAnnotationPresent(MyCustomAnnotation.class)) {
            MyCustomAnnotation annotation = method.getAnnotation(MyCustomAnnotation.class);
            System.out.println("Value of intKey: " + annotation.intKey());
            // Access other annotation attributes...
        }
        joinPoint.proceed();
    }
}
```

## Request Processing Flow

1. **HTTP Request** arrives at the server
2. **Servlet Filters** process the request (in order of their registration)
3. **DispatcherServlet** receives the request
4. **Interceptors' preHandle()** methods are called (in order of their registration)
5. **Controller** handles the request and produces a model and view
6. **Interceptors' postHandle()** methods are called (in reverse order)
7. **View Rendering** occurs
8. **Interceptors' afterCompletion()** methods are called (in reverse order)
9. **Servlet Filters** complete processing (in reverse order)
10. **HTTP Response** is sent back to the client

## Common Use Cases

### For Filters
- Authentication and authorization
- CORS (Cross-Origin Resource Sharing) handling
- Request/response logging
- Character encoding setting
- Compression/decompression
- Request/response transformation
- Caching

### For Interceptors
- Localization
- Theme selection
- Performance monitoring
- Request validation
- User tracking
- Audit logging
- Transaction management

### For Custom Annotations
- Method-level security
- Caching control
- Rate limiting
- Feature toggling
- Validation
- Audit logging

## Best Practices

1. **Use Filters for Servlet-level Concerns**: Authentication, encoding, etc.
2. **Use Interceptors for Spring MVC Concerns**: Logging, validation, etc.
3. **Keep Filter and Interceptor Logic Simple**: Focus on cross-cutting concerns
4. **Be Mindful of Order**: Set appropriate order for filters and interceptors
5. **Handle Exceptions Properly**: Especially in preHandle() methods
6. **Consider Performance Impact**: Filters and interceptors run on every matching request
7. **Use Path Patterns Wisely**: Apply filters and interceptors only where needed
8. **Document Custom Annotations Well**: Make their purpose and usage clear
9. **Test Thoroughly**: Ensure filters and interceptors work as expected in all scenarios
10. **Consider Security Implications**: Be careful with request/response modifications

## Related Resources
- [Spring MVC Interceptors Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-handlermapping-interceptor)
- [Servlet Filters Documentation](https://docs.oracle.com/javaee/7/tutorial/servlets011.htm)
- [Spring AOP Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop)
- [Custom Annotations in Spring](https://www.baeldung.com/spring-custom-annotation)
- [Filter vs Interceptor in Spring](https://www.baeldung.com/spring-mvc-handlerinterceptor-vs-filter)
