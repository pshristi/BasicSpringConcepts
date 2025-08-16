package com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class Payment {

    @Autowired
    Order order;

    public Payment() {
        System.out.println("Payment created");
    }
}
