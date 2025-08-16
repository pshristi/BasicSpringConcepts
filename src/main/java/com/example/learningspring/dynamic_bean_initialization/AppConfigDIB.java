package com.example.learningspring.dynamic_bean_initialization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigDIB {
    @Bean
    public OrderDIB getOrderBean(@Value("${isOnlineOrder:false") String isOnlineOrder) {
        boolean isOnlineOrder1 = Boolean.parseBoolean(isOnlineOrder);
        if(isOnlineOrder1) {
            return new OnlineOrderDIB();
        }
        else {
            return new OfflineOrderDIB();
        }
    }
}
