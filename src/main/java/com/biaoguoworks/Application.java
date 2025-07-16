package com.biaoguoworks;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wuxin
 * @date 2025/07/17 00:45:10
 */
@SpringBootApplication
@MapperScan("com.biaoguoworks")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        MockService bean = run.getBean(MockService.class);
        bean.insertSomeUsers();
    }
}