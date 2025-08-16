package com.example.learningspring.beanscope;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRequestScope {
    @PostConstruct
    public void init() {
        System.out.println("User initialized with hashcode: " + this.hashCode());
    }
}
