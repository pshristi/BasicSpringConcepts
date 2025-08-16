package com.example.learningspring.interceptorsAndFilters.customAnnotation;

import org.springframework.stereotype.Service;

@Service
public class TestCustomAnnotation {

    @MyCustomAnnotation(intKey = 1, stringKey = "Hello", classKey = String.class, enumKey = MyCustomEnum.VALUE2)
    public String getUserData() {
        return "User Data";
    }
}
