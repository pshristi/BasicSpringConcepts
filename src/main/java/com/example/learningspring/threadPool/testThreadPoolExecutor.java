package com.example.learningspring.threadPool;

import com.zaxxer.hikari.util.UtilityElf;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class testThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(2),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        executor.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try{
                    Thread.sleep(5000);
                    System.out.println("Thread: " + Thread.currentThread().getName() + " is running");
                }
                catch (Exception e) {
                    System.out.println("Error in thread: " + Thread.currentThread().getName());
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }

    public class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "Custom Thread");
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }

    public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task rejected: " + r.toString());
        }
    }
}
