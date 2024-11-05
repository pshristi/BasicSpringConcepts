package com.example.learningspring.transactions.programmatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class UserProgrammaticApproach2 {

    @Autowired
    TransactionTemplate transactionTemplate;

    public void updateUser(String userId, String name) {
        TransactionCallback<TransactionStatus> doOperationTask = (TransactionStatus status) -> {
            //Some DB Operation
            System.out.println("Updated user: " + userId + ", name: " + name);
            return status;
        };
        TransactionStatus status = transactionTemplate.execute(doOperationTask);
    }
}
