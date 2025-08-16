package com.example.learningspring.ioc.lazyInitialization;

import com.example.learningspring.ioc.lazyInitializationToAvoidCircularDependency.Payment;
import org.springframework.beans.factory.annotation.Autowired;

public class User1 {
    //Since Payment bean is lazy, it will not be created until it is needed i.e autowired here or called from another bean.
    @Autowired
    Payment payment;
}
