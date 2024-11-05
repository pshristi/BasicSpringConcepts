package com.example.learningspring.ioc;

import org.springframework.stereotype.Component;

@Component
public class WrongUser {
    String name;

    //Will not be able to use parameterized constructor with @Component annotation
    /*
    public WrongUser(String name) {
        this.name = name;
    }
     */
    public WrongUser() {
        this.name = "Wrong User1";
        System.out.println("User created : " + name);
    }
}
