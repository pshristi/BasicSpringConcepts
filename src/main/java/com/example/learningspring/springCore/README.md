# Spring Core Container (Classic XML Configuration)

This package rounds out [IoC & Dependency Injection](../ioc/README.md) with the underlying container mechanics and the classic XML-based configuration style that predates component scanning. Same concepts (beans, DI, lifecycle, autowiring) — different, older wiring style you'll still run into in legacy codebases.

## Spring Container: BeanFactory vs ApplicationContext
- Spring acts as a factory/container of beans and manages the whole lifecycle of an object.
- The starting point, before any container is involved, is a plain class with a `draw()` method:
```java
package org.test.javatest;

public class Triangle {
    public void draw() {
        System.out.println("Triangle drawn");
    }
}
```
- The minimal `spring.xml` that registers it as a bean lives on the classpath (e.g. `src` root or `resources`), with the standard Spring 2.0 DTD header:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN" "http://www.springframework.org/dtd/spring-beans-2.0.dtd">

<beans>
    <bean id="triangle" class="org.test.javatest.Triangle" />
</beans>
```
- `BeanFactory` / `XmlBeanFactory` is the original container interface. **`XmlBeanFactory` is deprecated and removed in modern Spring (5+) — historical reference only, do not use in new code:**
```java
package org.test.javatest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class DrawingApp {
    public static void main(String[] args) {
        // Legacy / deprecated — shown for historical context only
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring.xml"));
        Triangle triangle = (Triangle) factory.getBean("triangle");
        triangle.draw();
    }
}
```
- `ApplicationContext` (e.g. `ClassPathXmlApplicationContext`) is the modern equivalent — a superset of `BeanFactory` with extra functionality (event propagation, i18n via `MessageSource`, AOP integration). The same `DrawingApp` rewritten to the modern style:
```java
package org.test.javatest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DrawingApp {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        context.registerShutdownHook();
        Triangle triangle = (Triangle) context.getBean("triangle");
        triangle.draw();
    }
}
```
`registerShutdownHook()` is what lets the container run destroy callbacks on plain shutdown — see Bean Lifecycle Callbacks below.

## XML-Based Dependency Injection

### Setter Injection

**Primitive/String value** — `value="..."` wires a plain value via the matching setter:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <property name="type" value="Equilateral" />
</bean>
```
```java
package org.test.javatest;

public class Triangle {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void draw() {
        System.out.println("Triangle drawn: " + getType());
    }
}
```

**Object reference** — `ref="..."` wires another bean via its setter, instead of a literal value:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <property name="pointA" ref="pointA" />
    <property name="pointB" ref="pointB" />
    <property name="pointC" ref="pointC" />
</bean>

<bean id="pointA" class="org.test.javatest.Point">
    <property name="x" value="0" />
    <property name="y" value="0" />
</bean>
<bean id="pointB" class="org.test.javatest.Point">
    <property name="x" value="-20" />
    <property name="y" value="0" />
</bean>
<bean id="pointC" class="org.test.javatest.Point">
    <property name="x" value="20" />
    <property name="y" value="0" />
</bean>
```
`Point.java`, the plain value object referenced throughout this document:
```java
package org.test.javatest;

public class Point {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
```
`Triangle.java` wired with three `Point` properties:
```java
package org.test.javatest;

public class Triangle {
    private Point pointA;
    private Point pointB;
    private Point pointC;

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public Point getPointC() {
        return pointC;
    }

    public void setPointC(Point pointC) {
        this.pointC = pointC;
    }

    public void draw() {
        System.out.println("Point A = (" + getPointA().getX() + ", " + getPointA().getY() + ")");
        System.out.println("Point B = (" + getPointB().getX() + ", " + getPointB().getY() + ")");
        System.out.println("Point C = (" + getPointC().getX() + ", " + getPointC().getY() + ")");
    }
}
```

### Constructor Injection

**Primitive/String value**, via a constructor instead of a setter:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <constructor-arg value="Equilateral" />
</bean>
```
```java
package org.test.javatest;

public class Triangle {
    private String type;

    public Triangle(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void draw() {
        System.out.println("Triangle drawn: " + getType());
    }
}
```

**Object reference**:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <constructor-arg ref="pointA" />
    <constructor-arg ref="pointB" />
</bean>
```

**Multiple constructor arguments** — matched positionally by default. Adding a second `height` field and overloading the constructor:
```java
package org.test.javatest;

public class Triangle {
    private String type;
    private int height;

    public Triangle(String type) {
        this.type = type;
    }

    public Triangle(String type, int height) {
        this.type = type;
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public void draw() {
        System.out.println("Triangle drawn: " + getType() + ", height " + getHeight());
    }
}
```
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <constructor-arg value="Equilateral" />
    <constructor-arg value="20" />
</bean>
```
With two constructors present, Spring can no longer disambiguate purely by position/type — use `type` or `index` to force the correct overload:
```xml
<constructor-arg type="int" value="20" />
```
```xml
<constructor-arg index="0" value="Equilateral" />
<constructor-arg index="1" value="20" />
```

### Inner Beans
A bean scoped to a single use inside another bean's property — it doesn't need (and shouldn't have) an `id`:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <property name="pointA">
        <bean class="org.test.javatest.Point">
            <property name="x" value="0" />
            <property name="y" value="0" />
        </bean>
    </property>
</bean>
```

### Aliases and `idref`
A bean can be referenced by multiple names — via the `name` attribute (comma/space/semicolon-separated list) or a standalone `<alias>` tag:
```xml
<bean id="pointA" name="origin, zeroPoint" class="org.test.javatest.Point" />
<alias name="pointA" alias="startingPoint" />
```

**What `idref` actually does :** `idref` is *not* a substitute for `ref` when you need to inject an actual object. It only resolves to the target bean's **String id**, and its only advantage over a plain string literal is that Spring validates at container-startup time that the referenced id genuinely exists — catching typos early. Use it only where a property genuinely expects a bean's *name*, never as a stand-in for an object reference:
```xml
<!-- Correct use: the property expects the *name* of a bean, not the bean itself -->
<bean id="someService" class="org.test.javatest.SomeService">
    <property name="targetBeanName">
        <idref bean="pointA" /> <!-- resolves to the string "pointA", validated at startup -->
    </property>
</bean>

<!-- Incorrect (do not do this): idref used where an object reference (ref) was needed -->
<!-- <property name="pointA"><idref bean="zeroPoint"/></property> -->
<!-- This would NOT inject a Point object — only the string "zeroPoint". Use ref instead. -->
```

## Initializing Collections via XML
`<list>`, `<set>`, `<map>`, and `<props>` initialize collection-typed properties directly:
```xml
<bean id="triangle" class="org.test.javatest.Triangle">
    <property name="points">
        <list>
            <ref bean="pointA" />
            <ref bean="pointB" />
            <ref bean="pointC" />
        </list>
    </property>
</bean>
```
`Triangle.java` implementing the `List<Point>` side of that wiring:
```java
package org.test.javatest;

import java.util.List;

public class Triangle {
    private List<Point> points;

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void draw() {
        for (Point point : points) {
            System.out.println("Point = (" + point.getX() + ", " + point.getY() + ")");
        }
    }
}
```
When one bean definition inherits from another (see below), add `merge="true"` on the child's collection tag to combine parent and child entries instead of overriding the whole collection.

## XML-Based Autowiring
```xml
<bean id="triangle" class="org.test.javatest.Triangle" autowire="byName" />
```
- `byName`: the candidate bean's `id` must exactly match the property name.
- `byType`: exactly one bean of the matching type must exist in the container (ambiguous otherwise).
- `constructor`: same as `byType`, applied to constructor arguments.

## Bean Scopes (XML)
Declared via the `scope` attribute instead of `@Scope` — same scope names as the annotation form documented in [Bean Scopes](../beanscope/README.md):
```xml
<bean id="triangle" class="org.test.javatest.Triangle" scope="prototype" />
```

## ApplicationContextAware and BeanNameAware
Implementing `ApplicationContextAware` gives a bean a handle to the container itself; implementing `BeanNameAware` gives it its own bean id, as assigned in the XML. Both are commonly demonstrated together:
```java
package org.test.javatest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.factory.BeanNameAware;

public class Triangle implements ApplicationContextAware, BeanNameAware {
    private Point pointA;
    private Point pointB;
    private Point pointC;
    private ApplicationContext context = null;

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public Point getPointC() {
        return pointC;
    }

    public void setPointC(Point pointC) {
        this.pointC = pointC;
    }

    public void draw() {
        System.out.println("Point A = (" + getPointA().getX() + ", " + getPointA().getY() + ")");
        System.out.println("Point B = (" + getPointB().getX() + ", " + getPointB().getY() + ")");
        System.out.println("Point C = (" + getPointC().getX() + ", " + getPointC().getY() + ")");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("Bean name is: " + beanName);
    }
}
```
Both callbacks are invoked automatically by the container — no wiring in XML is required beyond the normal `<bean>` declaration. `setApplicationContext` hands the bean a live reference to the container (useful for pulling additional beans on demand, e.g. `context.getBean(...)`), while `setBeanName` reports the id the container registered this exact bean instance under.

## Bean Definition Inheritance
A bean can inherit configuration from another via the `parent` attribute — handy when several beans share most property values. Properties not overridden by the child are inherited as-is:
```xml
<bean id="parenttriangle" class="org.test.javatest.Triangle">
    <property name="pointA" ref="pointA" />
</bean>

<bean id="triangle1" class="org.test.javatest.Triangle" parent="parenttriangle">
    <property name="pointB" ref="pointB" />
    <property name="pointC" ref="pointC" />
</bean>

<bean id="triangle2" class="org.test.javatest.Triangle" parent="parenttriangle">
    <property name="pointB" ref="pointB" />
</bean>
```
`triangle1` and `triangle2` both inherit `pointA` from `parenttriangle` and add their own properties on top. Note: the source examples do **not** mark the parent bean `abstract="true"` — that attribute is optional and only needed if the parent bean should never be instantiated directly (e.g. it's missing required properties of its own).

**Collection merging with `merge="true"`** — by default, a child's collection property completely replaces the parent's; adding `merge="true"` combines parent and child entries instead:
```xml
<bean id="parenttriangle" class="org.test.javatest.Triangle">
    <property name="points">
        <list>
            <ref bean="pointA" />
        </list>
    </property>
</bean>

<!-- Merges: final list = [pointA, pointB] -->
<bean id="triangle1" class="org.test.javatest.Triangle" parent="parenttriangle">
    <property name="points">
        <list merge="true">
            <ref bean="pointB" />
        </list>
    </property>
</bean>

<!-- No merge, and this overrides "points" with a single ref instead of a list -->
<bean id="triangle2" class="org.test.javatest.Triangle" parent="parenttriangle">
    <property name="points" ref="pointB" />
</bean>
```

## Bean Lifecycle Callbacks
Three ways to hook into bean initialization/destruction:

1. **Spring interfaces**: `InitializingBean.afterPropertiesSet()` / `DisposableBean.destroy()`
2. **Custom methods** via `init-method` / `destroy-method` attributes on `<bean>`
3. **Global defaults** for every bean in the file, via `default-init-method` / `default-destroy-method` on the root `<beans>` tag:
```xml
<beans default-init-method="myInit" default-destroy-method="cleanUp">
    <bean id="triangle" class="org.test.javatest.Triangle" autowire="byName" />
</beans>
```

**1. Spring interfaces** — `Triangle` implementing both callback interfaces directly:
```java
package org.test.javatest;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Triangle implements InitializingBean, DisposableBean {
    private Point pointA;
    private Point pointB;
    private Point pointC;

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public Point getPointC() {
        return pointC;
    }

    public void setPointC(Point pointC) {
        this.pointC = pointC;
    }

    public void draw() {
        System.out.println("Point A = (" + getPointA().getX() + ", " + getPointA().getY() + ")");
        System.out.println("Point B = (" + getPointB().getX() + ", " + getPointB().getY() + ")");
        System.out.println("Point C = (" + getPointC().getX() + ", " + getPointC().getY() + ")");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean init method called for Triangle");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy method called for Triangle");
    }
}
```

**2. Custom methods** — dropping the interfaces in favor of plain methods wired via `init-method`/`destroy-method`:
```java
public void myInit() {
    System.out.println("My init method called for Triangle");
}

public void cleanUp() {
    System.out.println("My Cleanup method called for Triangle");
}
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN" "http://www.springframework.org/dtd/spring-beans-2.0.dtd">

<beans>
    <bean id="triangle" class="org.test.javatest.Triangle" autowire="byName" init-method="myInit" destroy-method="cleanUp">
    </bean>

    <bean id="pointA" class="org.test.javatest.Point">
        <property name="x" value="0" />
        <property name="y" value="0" />
    </bean>

    <bean id="pointB" class="org.test.javatest.Point">
        <property name="x" value="-20" />
        <property name="y" value="0" />
    </bean>

    <bean id="pointC" class="org.test.javatest.Point">
        <property name="x" value="20" />
        <property name="y" value="0" />
    </bean>
</beans>
```

**Verified execution order** (confirmed via console log output): the Spring interface method always runs *before* the custom method, both on init and on destroy:
```
InitializingBean init method called for Triangle
My init method called for Triangle
...
DisposableBean destroy method called for Triangle
My Cleanup method called for Triangle
```
This matches the modern annotation-based order documented in [ioc/README.md](../ioc/README.md): `@PostConstruct` runs before `InitializingBean`, which runs before a custom `init-method` — and the reverse order on destroy (`@PreDestroy` → `DisposableBean` → custom `destroy-method`).

To actually trigger destroy callbacks on a plain shutdown (outside a web container), register a shutdown hook:
```java
((AbstractApplicationContext) context).registerShutdownHook();
```

## BeanPostProcessor
Runs for **every** bean in the container, right after each bean is instantiated — used to extend or inspect beans generically:
```java
public class DisplayNameBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("In Before Initialization method. Bean name is " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("In After Initialization method. Bean name is " + beanName);
        return bean;
    }
}
```
Registered as a plain bean with **no `id`** — Spring auto-detects it and applies it to every other bean in the container:
```xml
<bean class="org.test.javatest.DisplayNameBeanPostProcessor" />
```

## BeanFactoryPostProcessor and PropertyPlaceholderConfigurer
`BeanFactoryPostProcessor` runs even earlier than `BeanPostProcessor` — at the **bean-definition/metadata** stage, before any bean is instantiated:
```java
public class MyBeanFactoryPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("My Bean Factory Post Processor is called");
    }
}
```
```xml
<bean class="org.test.javatest.MyBeanFactoryPP" />
```

**`PropertyPlaceholderConfigurer`** is Spring's out-of-the-box `BeanFactoryPostProcessor` that resolves `${...}` placeholders in bean XML against an external `.properties` file:
```properties
# pointsconfig.properties
pointA.pointX=0
pointA.pointY=0
```
```xml
<bean id="pointA" class="org.test.javatest.Point">
    <property name="x" value="${pointA.pointX}" />
    <property name="y" value="${pointA.pointY}" />
</bean>

<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="locations" value="pointsconfig.properties" />
</bean>
```

## Classic Annotation Wiring (pre component-scan era)
These annotations each need their supporting `BeanPostProcessor` registered explicitly in XML — unless the single shortcut tag below is used instead.

**`@Required`** — marks a setter as mandatory; startup fails if the property is never set via XML.
```java
@Required
public void setCenter(Point center) { this.center = center; }
```
```xml
<bean class="org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor" />
```

**`@Autowired`** — replaces XML `<property ref="...">` wiring; Spring injects a matching bean automatically **by type**.
```xml
<bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor" />
```

**Conflict**: if more than one bean of the matching type exists, `@Autowired` fails with an ambiguous-dependency error. Two ways to resolve it:
1. Name the target bean the same as the field/property being autowired — autowiring falls back to a by-name tie-break.
2. Use `@Qualifier` on the injection point plus a matching `<qualifier>` tag on the candidate bean:
```java
@Autowired
@Qualifier("circleRelated")
public void setCenter(Point center) { this.center = center; }
```
```xml
<bean id="pointA" class="org.test.javatest.Point">
    <qualifier value="circleRelated" />
</bean>
```

**Single shortcut tag** — instead of registering `RequiredAnnotationBeanPostProcessor` / `AutowiredAnnotationBeanPostProcessor` / the JSR-250 processor individually, one tag enables them all:
```xml
<context:annotation-config/>
```

## JSR-250 Annotations
*(The source notes label this section "JSR-20" — the correct specification number is **JSR-250**.)*

**`@Resource`** — dependency injection **by name** (matches the bean whose id/name equals the given `name`, unlike `@Autowired`'s by-type matching):
```java
@Resource(name = "pointC")
public void setCenter(Point center) { this.center = center; }
```

**`@PostConstruct`** / **`@PreDestroy`** — same lifecycle-callback role as `InitializingBean`/`DisposableBean`, just via annotation. See [ioc/README.md](../ioc/README.md) Bean Lifecycle for the fuller worked example; this entry mainly exists to correct the JSR number and connect it to the interface/XML forms above.

## @Component and Stereotype Annotations
- `@Component` marks a class for auto-detection as a bean.
- More specific **stereotype annotations** exist for standard architectural roles and read more clearly than a bare `@Component`: `@Repository` (data access), `@Service` (business logic), `@Controller` / `@RestController` (web layer) — see [Layered Architecture](../layeredArchitecture/README.md) for how these map onto real project layers.
- Auto-detection requires component scanning to be enabled for the relevant package:
```xml
<context:component-scan base-package="org.test.javatest" />
```

## MessageSource (i18n / externalized text)
`ApplicationContext` provides `MessageSource` for looking up text from a `.properties` file:
```properties
# mymessages.properties
greeting=Hello!
drawing.circle=Drawing Circle!
drawing.point=Circle: Point is: {0}, {1}
```
```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basenames">
        <list><value>mymessages</value></list>
    </property>
</bean>
```
Directly off the context (no injection needed):
```java
context.getMessage("greeting", null, "Default Greeting", null);
```
Or injected into a bean for internal use:
```java
@Autowired
private MessageSource messageSource;
// ...
messageSource.getMessage("greeting", null, "Default Greeting", null);
```
With arguments — `{0}`, `{1}` placeholders in the properties file are filled positionally from the `Object[]`:
```java
messageSource.getMessage(
    "drawing.point",
    new Object[] { center.getX(), center.getY() },
    "Default Drawing Message",
    null
);
```

## Event Handling
Three pieces: a custom event, a listener, and a publisher.
```java
// Custom event
public class DrawEvent extends ApplicationEvent {
    public DrawEvent(Object source) {
        super(source);
    }

    public String toString() {
        return "Draw Event Occurred";
    }
}

// Listener — auto-detected via @Component; invoked for every published ApplicationEvent
@Component
public class MyEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}

// Publisher — implement ApplicationEventPublisherAware to receive the publisher, then call publishEvent
public class Circle implements Shape, ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void draw() {
        DrawEvent drawEvent = new DrawEvent(this);
        publisher.publishEvent(drawEvent);
    }
}
```

## Spring Boot Basics
- **Maven**: a build/dependency-management tool; `pom.xml` holds all project configuration and dependency coordinates.
- Version format `MajorVersion.MinorVersion.Patch`; a `-SNAPSHOT` suffix (e.g. `0.0.1-SNAPSHOT`) marks a build still under active development.
- `@SpringBootApplication`: marks the application's entry-point class (bundles `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`).
- `SpringApplication.run(YourApplication.class, args)`: bootstraps the embedded servlet container and starts the app.
- `spring-boot-starter-web` pulls in Spring MVC automatically — see [Request at Controller](../requestAtController/README.md) for the full request-handling flow.
- `@RestController` + `@RequestMapping`: marks a class as a REST controller and maps a URL path to a handler method; return values are converted to JSON by default (via Jackson).
- **Business Service pattern**: a `@Service`-annotated class is typically a singleton — Spring creates one instance at startup, keeps it in the container, and every class with an `@Autowired` field of that type shares the same instance. See [Bean Scopes](../beanscope/README.md) for singleton details and [Layered Architecture](../layeredArchitecture/README.md) for where the service layer sits.
- **Dependency Injection / Inversion**, in one line: instead of a class hardcoding and constructing what it depends on, the dependency is removed from the class and supplied ("injected") from outside — see [IoC](../ioc/README.md) for the full picture.

---

## Related Resources
- [IoC & Dependency Injection (annotation-based)](../ioc/README.md)
- [Bean Scopes](../beanscope/README.md)
- [Layered Architecture](../layeredArchitecture/README.md)
- [Request at Controller](../requestAtController/README.md)
- [Aspect-Oriented Programming (AOP)](../aop/README.md)
- [Spring Framework Core Container Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans)
