package com.biaoguoworks;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public void insertSomeUsers() throws InterruptedException {
        userMapper.insertUser("我是主");
        Thread thread = Thread.startVirtualThread(() ->mockService.Insert2() );
        thread.join();
        System.out.println(1 / 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void Insert2(){
        userMapper.insertUser("我是虚拟");

    }



}
