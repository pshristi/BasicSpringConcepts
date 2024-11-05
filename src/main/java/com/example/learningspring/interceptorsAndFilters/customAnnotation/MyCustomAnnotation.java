package com.example.learningspring.interceptorsAndFilters.customAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCustomAnnotation {

    int intKey() default 0;

    String stringKey() default "defaultKey";

    Class<?> classKey() default Object.class;

    MyCustomEnum enumKey() default MyCustomEnum.VALUE1;
}
