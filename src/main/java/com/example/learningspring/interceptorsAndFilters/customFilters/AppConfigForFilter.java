package com.example.learningspring.interceptorsAndFilters.customFilters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigForFilter {

    @Bean
    public FilterRegistrationBean<MyFilter1> getMyFilter1() {
        FilterRegistrationBean<MyFilter1> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter1());
        registrationBean.addUrlPatterns("/*"); // URL patterns to apply the filter
        registrationBean.setOrder(2); // Order of the filter in the filter chain
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> getMyFilter2() {
        FilterRegistrationBean<MyFilter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter2());
        registrationBean.addUrlPatterns("/*"); // URL patterns to apply the filter
        registrationBean.setOrder(1); // Order of the filter in the filter chain
        return registrationBean;
    }
}
