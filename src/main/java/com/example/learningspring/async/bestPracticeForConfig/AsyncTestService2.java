package com.example.learningspring.async.bestPracticeForConfig;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService2 {
    //@EnableAsync annotation should be used for async to work
    @Async
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }

    //Now @Async("customTaskExecutor") and @Async both will work
}
