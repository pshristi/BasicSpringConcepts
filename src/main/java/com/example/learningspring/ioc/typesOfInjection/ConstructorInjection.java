package com.example.learningspring.ioc.typesOfInjection;

import com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class ConstructorInjection {
    final Order order;

    //It is fail fast
    @Autowired
    public ConstructorInjection(Order order) {
        this.order = order;
    }
}
