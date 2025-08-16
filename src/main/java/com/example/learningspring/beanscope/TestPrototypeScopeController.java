package com.example.learningspring.beanscope;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestPrototypeScopeController {

    @Autowired
    private UserPrototypeScope user;

    @Autowired
    private StudentPrototypeScope student;

    @PostConstruct
    public void init() {
        System.out.println("TestScopeController created with hashcode: " + this.hashCode());
        System.out.println("User created with hashcode: " + user.hashCode());
        System.out.println("Student created with hashcode: " + student.hashCode());
    }

    public static void main(String[] args) {

    }
}
