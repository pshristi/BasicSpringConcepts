package com.example.learningspring.async.bestPracticeForConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncTestController2 {

    @Autowired
    AsyncTestService2 asyncTestService2;

    @GetMapping("/getUser")
    public String getUser(){
        System.out.println("Inside getUser() method : " + Thread.currentThread().getName());
        //Every call to this method will create a new thread
        asyncTestService2.getAsyncData();
        return "User Name: John Doe";
    }
}
