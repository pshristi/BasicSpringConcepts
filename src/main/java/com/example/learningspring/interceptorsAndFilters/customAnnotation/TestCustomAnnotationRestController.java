package com.example.learningspring.interceptorsAndFilters.customAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCustomAnnotationRestController {

    @Autowired
    TestCustomAnnotation testCustomAnnotation;

    @GetMapping("/userData")
    public String getUserData() {
        return testCustomAnnotation.getUserData();
    }
}
