package com.biaoguoworks;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * @author wuxin
 * @date 2025/07/17 00:53:35
 */
@Component
public class MockService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MockService mockService;
    @Resource
    private PlatformTransactionManager platformTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;

    public void insertSomeUsers() {
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            userMapper.insertUser("我是主");
            Thread thread = Thread.startVirtualThread(() ->mockService.Insert2() );
            thread.join();
            System.out.println(1 / 0);
            platformTransactionManager.commit(transaction);
        }catch (Exception e){
            platformTransactionManager.rollback(transaction);
        }
    }

    public void Insert2(){
        userMapper.insertUser("我是虚拟");

    }



}
