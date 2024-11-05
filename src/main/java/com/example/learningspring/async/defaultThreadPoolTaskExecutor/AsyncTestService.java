package com.example.learningspring.async.defaultThreadPoolTaskExecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService {

    //@EnableAsync annotation should be used for async to work
    @Async
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }

    //How async works?
    //On startup, Spring (AsyncExecutionInterceptor) try to get any ThreadPoolTaskExecutor (getDefaultExecutor()),
    //if it doesn't exist, it uses SimpleAsyncTaskExecutor
}
