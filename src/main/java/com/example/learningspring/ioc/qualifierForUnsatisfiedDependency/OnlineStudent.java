package com.example.learningspring.ioc.qualifierForUnsatisfiedDependency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("onlineStudent")
@Component
public class OnlineStudent implements Student {
}
