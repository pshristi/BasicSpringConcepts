package com.example.learningspring.transactions.declarative;

import org.springframework.transaction.annotation.Transactional;

public class UserDeclarative {
    @Transactional(transactionManager = "userTransactionManager")
    public void updateUser(String userId, String name) {
        //Some DB Operation
        System.out.println("Updated user: " + userId + ", name: " + name);
    }
}
