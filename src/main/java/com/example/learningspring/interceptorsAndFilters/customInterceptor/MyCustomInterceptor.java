package com.example.learningspring.interceptorsAndFilters.customInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MyCustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Inside MyCustomInterceptor.preHandle()");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Inside MyCustomInterceptor.postHandle()");
    }

    //This method is invoked even if exception occurs during request processing
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       System.out.println("Inside MyCustomInterceptor.afterCompletion()");
    }
}

//Interceptors are invoked by DispatcherServlet (doDispatch()) before/after api invocation,
// to run custom logic even before request reached specific controolers
