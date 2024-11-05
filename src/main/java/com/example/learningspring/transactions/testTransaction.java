package com.example.learningspring.transactions;

public class testTransaction {
    /*
    @EnableTransactionManagement is responsible for enabling transaction management
    TransactionInterceptor
    |--> TransactionAspectSupport class invokeWithinTransaction method is responsible for managing transactional behavior

    */
    /*
    TransactionManager <<interface>>
    |-->
    PlatformTransactionManager extends TransactionManager <<interface>>
    |--> AbstractPlatformTransactionManager <<abstract class>>
    |--> HibernateTransactionManager, DataSourceTransactionManager, JtaTransactionManager, JpaTransactionManager, etc. <<concrete classes>>
     */
}
