package com.example.learningspring.ioc.typesOfInjection;

import com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjection {
    //Cannot create immutable object when setter injection is being used for Dependency Injection
    //final Order order;
    Order order;

    @Autowired
    public void setOrder(Order order) {
        this.order = order;
    }
}
