package com.example.learningspring.async.bestPracticeForConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig1 implements AsyncConfigurer {

    private ThreadPoolExecutor poolExecutor;

    @Override
    public synchronized Executor getAsyncExecutor() {
        if (poolExecutor == null) {
            poolExecutor = new ThreadPoolExecutor(3, 5, 60L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10));

            poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return poolExecutor;
    }
}
