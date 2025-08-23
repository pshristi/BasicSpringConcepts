package com.example.learningspring.ioc.qualifierForUnsatisfiedDependency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//Use Primary to mark a class if no Qualifier is used to resolve the ambiguity between two implementations of the same interface for Dependency Injection.
@Primary
@Component
@Qualifier("offlineStudent")
public class OfflineStudent implements Student {
}
