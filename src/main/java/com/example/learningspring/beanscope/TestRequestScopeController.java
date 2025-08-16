package com.example.learningspring.beanscope;

import com.example.learningspring.LearningSpringApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("singleton")
public class TestRequestScopeController {
    //Using scope proxy on user, we do not really create new user instance but just use proxy for this bean creation
    @Autowired
    UserRequestScope user;

    @PostConstruct
    public void init() {
        System.out.println("User created with hashcode: " + user.hashCode());
        System.out.println("TestRequestScopeController created with hashcode: " + this.hashCode());
    }
}
