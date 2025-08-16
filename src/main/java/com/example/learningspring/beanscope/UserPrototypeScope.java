package com.example.learningspring.beanscope;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class UserPrototypeScope {

    @PostConstruct
    public void init() {
        System.out.println("User initialized with hashcode: " + this.hashCode());
    }
}

