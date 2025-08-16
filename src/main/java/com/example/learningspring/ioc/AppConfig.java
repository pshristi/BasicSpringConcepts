package com.example.learningspring.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CorrectUser getCorrectUser1() {
        return new CorrectUser("Correct User 1");
    }

    @Bean
    public CorrectUser getCorrectUser2() {
        return new CorrectUser("Correct User 2");
    }
}
