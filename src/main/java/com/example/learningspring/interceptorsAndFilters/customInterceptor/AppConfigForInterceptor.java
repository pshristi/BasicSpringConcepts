package com.example.learningspring.interceptorsAndFilters.customInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfigForInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Interceptors will be applied in the order they are added
        registry.addInterceptor(new MyCustomInterceptor())
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/exclude"); // Exclude specific paths

        registry.addInterceptor(new MyCustomInterceptor1())
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/delete");
    }
}
