package com.biaoguoworks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author wuxin
 * @date 2025/07/17 00:45:10
 */
@SpringBootApplication
@MapperScan("com.biaoguoworks")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class dadadawApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(dadadawApplication.class, args);
        ExecutorService vtThreadPool = run.getBean("vtThreadPool", ExecutorService.class);
        vtThreadPool.execute(()->{
            for (int i = 0; i < 10000; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("我响应中断咯+++++++++++++++++++现在退出！！！");
                    System.out.flush();
                    return;
                }
                System.out.println("运行位置" + i + run.isActive());
                System.out.flush();
            }

        });
        Thread.sleep(5000L);
        System.out.println("执行容器关闭容器xxxxxxxxxxx");
        System.out.flush();

    }

//    @Bean
    public DisposableBean executorDisposer(Executor vtThreadPool) {
        return () -> {
            System.out.println("-------------------------------------------------------------------------先在开始关闭");
            System.out.flush();
            if (vtThreadPool instanceof ExecutorService executorService) {
                executorService.shutdown();
                List<Runnable> tasks = null;
                try {
                    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        tasks  = executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    tasks = executorService.shutdownNow();
                }finally {
                    if(tasks != null){
                        tasks.forEach(e->{
                            if(e instanceof Future<?>){
                                ((Future<?>) e).cancel(true);
                            }
                        });
                    }
                }
            }
        };
    }

    @Bean(name = "vtThreadPool")
    public Executor financeBackedServiceExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * IntStream.range(1, 1000).forEach(i->{
     * <p>
     * try {
     * TimeUnit.SECONDS.sleep(1);
     * } catch (InterruptedException e) {
     * throw new RuntimeException(e);
     * }
     * System.out.println("--------------------先在是" + i);
     * });
     */


//    @Bean(name = "asyncServiceExecutor")
//    public Executor asyncServiceExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(2);
//        //配置最大线程数
//        executor.setMaxPoolSize(4);
//        //配置队列大小
//        executor.setQueueCapacity(100);
//        //配置线程池中的线程的名称前缀
//        executor.setThreadNamePrefix("asyncServiceExecutor");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }

    private static final void printer() {
        IntStream.range(1, 10000).forEach(i -> {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("--------------------先在是" + i);
        });
    }

}