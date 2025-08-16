package com.example.learningspring.async.returnValueUsingFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncTestService3 {

    @Async
    public Future<String> getAsyncData() {
        System.out.println("Inside getAsyncData() method : " + Thread.currentThread().getName());
        return new AsyncResult<>("Async data returned");
    }
}
