package com.example.learningspring.async.returnValueUsingFuture;

import com.example.learningspring.async.bestPracticeForConfig.AsyncTestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class AsyncTestController3 {

    @Autowired
    AsyncTestService3 asyncTestService3;

    @GetMapping("/getUser")
    public String getUser() {
        System.out.println("Inside getUser() method : " + Thread.currentThread().getName());
        //Every call to this method will create a new thread
        Future<String> result = asyncTestService3.getAsyncData();

        String resultString = "User data not available";
        //Wait for the result to be ready
        try {
            resultString = result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
