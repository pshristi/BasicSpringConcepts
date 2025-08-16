package com.example.learningspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BasicSpringConceptsApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BasicSpringConceptsApplication.class, args);
    }

}
