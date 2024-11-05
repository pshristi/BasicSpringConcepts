package com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Order {

    //Use lazy with autowired to avoid circular dependencies. This will create proxy until bean is really used
    @Lazy
    @Autowired
    Payment payment;

    public Order() {
        System.out.println("Order created");
    }
}
