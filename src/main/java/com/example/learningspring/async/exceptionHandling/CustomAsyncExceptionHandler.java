package com.example.learningspring.async.exceptionHandling;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Handling uncaught exception in async method: " + method.getName());
    }
}

//When my async method have a return type, then I can get any exception in the caller method when I call Future.get()
//And can handle it there.
//When my async method doesn't have a return type, then I need to implement handleUncaughtException method
//of AsyncUncaughtExceptionHandler to handle exceptions in async methods.
