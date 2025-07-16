package com.biaoguoworks;

import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuxin
 * @date 2025/07/17 00:53:35
 */
@Component
public class MockService {

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertSomeUsers(){
        userMapper.insertUser();
//        Thread.startVirtualThread(()->{
//
//        })
    }



}
