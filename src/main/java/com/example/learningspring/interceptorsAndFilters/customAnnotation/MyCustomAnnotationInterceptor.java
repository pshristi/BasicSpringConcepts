package com.example.learningspring.interceptorsAndFilters.customAnnotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class MyCustomAnnotationInterceptor {
    @Around("@annotation(com.example.learningspring.interceptorsAndFilters.customAnnotation.MyCustomAnnotation.class)")
    public void invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before executing method: " + joinPoint.getSignature().getName());

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if(method.isAnnotationPresent(MyCustomAnnotation.class)) {
            MyCustomAnnotation annotation = method.getAnnotation(MyCustomAnnotation.class);
            System.out.println("Value of intKey: " + annotation.intKey());
            System.out.println("Value of stringKey: " + annotation.stringKey());
            System.out.println("Value of classKey: " + annotation.classKey().getName());
            System.out.println("Value of enumKey: " + annotation.enumKey());
        }

        joinPoint.proceed();

        System.out.println("After executing method: " + joinPoint.getSignature().getName());
    }
}
