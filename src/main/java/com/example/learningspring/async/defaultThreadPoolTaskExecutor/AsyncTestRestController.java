package com.example.learningspring.async.defaultThreadPoolTaskExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncTestRestController {

    @Autowired
    AsyncTestService asyncTestService;

    @GetMapping("/getUser")
    public String getUser() {
        System.out.println("Inside getUser() method : " + Thread.currentThread().getName());
        //Every call to this method will create a new thread
        asyncTestService.getAsyncData();
        return "User Name: John Doe";
    }
}

