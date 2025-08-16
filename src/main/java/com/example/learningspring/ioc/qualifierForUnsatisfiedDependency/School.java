package com.example.learningspring.ioc.qualifierForUnsatisfiedDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class School {
    String name;

    //If no Qualifier is provided, Spring will try to autowire Primary one
    @Autowired
    Student student;

    @Autowired
    @Qualifier(value = "onlineStudent")
    Student onlineStudent;
}
