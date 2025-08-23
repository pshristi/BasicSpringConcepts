package com.example.learningspring.async.customThreadPoolTaskExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncTestRestController1 {

    @Autowired
    AsyncTestService1 asyncTestService1;

    @GetMapping("/getUser")
    public String getUser() {
        System.out.println("Inside getUser() method : " + Thread.currentThread().getName());
        //Every call to this method will create a new thread
        asyncTestService1.getAsyncData();
        return "User Name: John Doe";
    }

}
