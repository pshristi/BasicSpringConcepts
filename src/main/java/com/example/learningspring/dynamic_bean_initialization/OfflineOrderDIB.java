package com.example.learningspring.dynamic_bean_initialization;

public class OfflineOrderDIB implements OrderDIB{
    @Override
    public String createOrder() {
        return "Offline Order Created";
    }
}
