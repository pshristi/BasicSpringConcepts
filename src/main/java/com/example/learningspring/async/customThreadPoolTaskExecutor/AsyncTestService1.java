package com.example.learningspring.async.customThreadPoolTaskExecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService1 {
    //@EnableAsync annotation should be used for async to work
    @Async("customTaskExecutor")
    void getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
    }

    //If I provide bean for ThreadPoolTaskExecutor then @Async("customTaskExecutor") and @Async both will work
    //But, if i provided bean for ThreadPoolExecutor rather than ThreadPoolTaskExecutor then only @Async("customTaskExecutor") will work
    //else, @Async will pick up SimpleAsyncTaskExecutor
}
