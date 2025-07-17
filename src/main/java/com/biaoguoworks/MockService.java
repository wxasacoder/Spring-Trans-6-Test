package com.biaoguoworks;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.util.*;

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
        HashSet<TransactionSynchronization> transactionSynchronizations = new HashSet<>(TransactionSynchronizationManager.getSynchronizations());
        try {
            Map<Object, Object> resourceMap = TransactionSynchronizationManager.getResourceMap();
            userMapper.insertUser("我是主");
            Class<TransactionSynchronizationManager> transactionSynchronizationManagerClass = TransactionSynchronizationManager.class;
            Thread thread = Thread.startVirtualThread(() -> {
                try {
                    Field resources = transactionSynchronizationManagerClass.getDeclaredField("resources");
                    resources.setAccessible(true);
                    ThreadLocal<Map<Object, Object>> o = (ThreadLocal<Map<Object, Object>>)resources.get(null);
                    o.set(resourceMap);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }


                try {
                    Field resources = transactionSynchronizationManagerClass.getDeclaredField("synchronizations");
                    resources.setAccessible(true);
                    ThreadLocal<Set<TransactionSynchronization>> o = (ThreadLocal<Set<TransactionSynchronization>>)resources.get(null);
                    o.set(transactionSynchronizations);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }


                TransactionSynchronizationManager.setCurrentTransactionName(TransactionSynchronizationManager.getCurrentTransactionName());
                TransactionSynchronizationManager.setCurrentTransactionReadOnly(TransactionSynchronizationManager.isCurrentTransactionReadOnly());
                TransactionSynchronizationManager.setCurrentTransactionIsolationLevel(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
                TransactionSynchronizationManager.setActualTransactionActive(TransactionSynchronizationManager.isSynchronizationActive());
                try {
                    mockService.Insert2();
                } finally {
                    TransactionSynchronizationManager.clear();
                }
            });
            thread.join();
            System.out.println(1 / 0);
            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            platformTransactionManager.rollback(transaction);
        }
    }

    public void Insert2() {
        userMapper.insertUser("我是虚拟");

    }


}
