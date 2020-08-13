package com.hong.userservice.config;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * @author liang
 * @description
 * @date 2020/7/14 11:28
 */
@Component
public class MyTransaction {

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 开启事务
     */
    public TransactionStatus begin() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        System.out.println("开启事务");
        // 得到事务状态
        return transaction;
    }

    /**
     * 提交事务
     */
    public void commit(TransactionStatus transaction) {
        transactionManager.commit(transaction);
        System.out.println("提交事务");
    }

    /**
     * 回滚事务
     */
    public void rollback(TransactionStatus transaction) {
        transactionManager.rollback(transaction);
        System.out.println("事务回滚");
    }
}
