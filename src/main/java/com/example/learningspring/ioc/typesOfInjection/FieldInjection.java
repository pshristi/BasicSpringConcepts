package com.example.learningspring.ioc.typesOfInjection;

import com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjection {
    //Cannot create immutable object when "field injection" is being used for Dependency Injection
    //@Autowired
    //final Order order;
    @Autowired
    Order order;

    public FieldInjection() {
        System.out.println("Field injection bean created");
    }
}
