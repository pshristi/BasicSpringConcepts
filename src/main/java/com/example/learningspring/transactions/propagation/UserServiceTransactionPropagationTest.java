package com.example.learningspring.transactions.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class UserServiceTransactionPropagationTest {

    @Autowired
    UserDaoTransactionPropagationTest userDaoTransactionPropagationTest;

    @Transactional
    public void method1() {
        System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
        //Do some database operations
        userDaoTransactionPropagationTest.method2();
        //Do more database operations
    }
}
