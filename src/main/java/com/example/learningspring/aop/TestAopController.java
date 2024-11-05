package com.example.learningspring.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAopController {
    @GetMapping("/test-aop-execution")
    public void testAop() {
        System.out.println("api call test-aop-execution");
    }
}
