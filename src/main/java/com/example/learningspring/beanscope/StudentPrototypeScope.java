package com.example.learningspring.beanscope;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class StudentPrototypeScope {

    @Autowired
    private UserPrototypeScope user;

    @PostConstruct
    public void init() {
        System.out.println("Student created with hashcode: " + this.hashCode());
        System.out.println("User created with hashcode: " + user.hashCode());
    }
}
