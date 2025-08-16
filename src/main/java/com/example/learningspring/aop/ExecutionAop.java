package com.example.learningspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecutionAop {

    //On startup, PointcutParser parses the pointcut expression and stores eligible classes to a required data structure.
    //After that AbstractAutoProxyCreator creates a proxy for each eligible class.
    //JDK default proxy or CGLIB proxy will be created.
    //DefaultAopProxyFactory, CglibAopProxy, JdkDynamicAopProxy
    //ReflectiveMethodInvocation

    @Pointcut("execution(* com.example.learningspring.aop.TestAopController.*(..))")
    public void customPointcut() {

    }
    @Before("customPointcut()")
    public void before() {
        System.out.println("before execution aop");
    }
    
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around execution aop 1");
        joinPoint.proceed();
        System.out.println("around execution aop 2");
    }
}
