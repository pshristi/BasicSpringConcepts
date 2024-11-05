package com.example.learningspring.ioc;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;

public class CorrectUser {
    String userName;

    @PostConstruct
    public void init() {
        System.out.println("Correct user bean is created and all dependencies injected");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Correct user bean is destroyed");
    }

    public CorrectUser(String userName) {
        this.userName = userName;
        System.out.println("User created : " + userName);
    }
}
