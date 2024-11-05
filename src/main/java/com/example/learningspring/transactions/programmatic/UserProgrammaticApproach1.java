package com.example.learningspring.transactions.programmatic;

import jakarta.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

public class UserProgrammaticApproach1 {
    @Autowired
    PlatformTransactionManager userTransactionManager;

    public void updateUser(String userId, String name) {
        TransactionStatus status = userTransactionManager.getTransaction(null);
        try{
            //Some DB Operation
            System.out.println("Updated user: " + userId + ", name: " + name);
            userTransactionManager.commit(status);
        }
        catch (Exception e) {
            userTransactionManager.rollback(status);
        }
    }
}
