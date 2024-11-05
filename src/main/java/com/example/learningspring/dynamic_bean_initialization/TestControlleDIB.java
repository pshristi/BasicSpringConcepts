package com.example.learningspring.dynamic_bean_initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControlleDIB {

    @Autowired
    OrderDIB orderDIB;

    @GetMapping("/testDIB")
    public String testDIB() {
        return orderDIB.createOrder();
    }

}
