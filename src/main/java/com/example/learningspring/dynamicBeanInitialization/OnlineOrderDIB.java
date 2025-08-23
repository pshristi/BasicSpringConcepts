package com.example.learningspring.dynamic_bean_initialization;

public class OnlineOrderDIB implements OrderDIB {

    @Override
    public String createOrder() {
        return "Online Order Created";
    }
}
