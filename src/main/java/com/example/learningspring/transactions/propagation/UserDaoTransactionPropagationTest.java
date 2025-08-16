package com.example.learningspring.transactions.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class UserDaoTransactionPropagationTest {

    @Autowired
    TransactionTemplate userTransactionTemplateForPropagationTesting;

    @Autowired
    PlatformTransactionManager userTransactionManager;

    @Transactional
    public void method2() {
        System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
        //Do some database operations
    }

    //Programmatic transaction management
    public void method3ProgrammaticApproach1() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setName("myTransaction");
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = userTransactionManager.getTransaction(transactionDefinition);
        try {
            System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
            System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
            //Do some database operations
            userTransactionManager.commit(status);
        }
        catch (Exception e) {
            userTransactionManager.rollback(status);
        }
    }

    //Programmatic transaction management
    public void method4ProgrammaticApproach2() {
        TransactionCallback<TransactionStatus> doOperations = (TransactionStatus status) -> {
            System.out.println("Current transaction active : " + TransactionSynchronizationManager.isActualTransactionActive());
            System.out.println("Current transaction name : " + TransactionSynchronizationManager.getCurrentTransactionName());
            //Do some database operations
            return status;
        };
        userTransactionTemplateForPropagationTesting.execute(doOperations);
    }
}
