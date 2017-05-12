package com.xms.task.config;

import com.google.common.cache.CacheBuilder;
import com.xms.task.lib.AppAsyncUncaughtExceptionHandler;
import com.xms.task.lib.ScheduleErrorHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@Configuration
@Import(value = {})
@ComponentScan(basePackages = {"com.xms.task.service", "com.xms.task.component", "com.xms.task.repository"})
@PropertySource(value = {"classpath:application.properties"})
// cgLib 可以不用接口，它底层调用asm 动态生成一个代理类去覆盖父类中非 final 的方法，然后实现 MethodInterceptor 接口的
// intercept 方法，这样以后直接调用重写的方法，比 JDK 要快。但是加载 cglib 消耗时间比直接 JDK
// 反射时间长，开发的过程中，如果是反复动态生成新的代理类推荐用 JDK 自身的反射，反之用 cglib。
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@EnableCaching
public class AppConfig extends DefaultConnectionKeepAliveStrategy implements AsyncConfigurer, EnvironmentAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 保持最多的连接数
    private static final int CONNECTION_MAX_TOTAL = 50;

    // domain最多连接数
    private static final int CONNECTION_MAX_ROUTE = 10;

    @Resource
    private Environment env;

    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("-async-task-");
        executor.initialize();

        return executor;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AppAsyncUncaughtExceptionHandler();
    }

    @Bean
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("-schedule-task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(new ScheduleErrorHandler());
        scheduler.initialize();

        return scheduler;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(96);
        pool.setMaxPoolSize(Integer.MAX_VALUE);
        pool.setQueueCapacity(96);
        pool.setThreadNamePrefix("-spring-task-");
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }

    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(10 * 1000).build());
        connectionManager.setDefaultConnectionConfig(ConnectionConfig.custom().setBufferSize(8 * 1024).setFragmentSizeHint(8 * 1024).build());
        connectionManager.setMaxTotal(CONNECTION_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(CONNECTION_MAX_ROUTE);

        return connectionManager;
    }

    @Bean
    public HttpClient httpClient() {

        HttpClientBuilder builder = HttpClients.custom();

        builder.setKeepAliveStrategy(this);
        builder.setConnectionManager(poolingHttpClientConnectionManager());

        return builder.build();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        GuavaCache appCache = new GuavaCache(Constants.APP_CACHE, CacheBuilder.newBuilder().build());
        simpleCacheManager.setCaches(Arrays.asList(appCache));
        return simpleCacheManager;
    }

    @Bean
    public MapperScannerConfigurer taskScannerConfigurer() {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setSqlSessionFactoryBeanName("basicSqlSessionFactory");
        config.setBasePackage("com.nd.task.repository.mysql.mapper.task");
        return config;
    }

    @Bean
    public MapperScannerConfigurer logScannerConfigurer() {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setSqlSessionFactoryBeanName("logSqlSessionFactory");
        config.setBasePackage("com.nd.task.repository.mysql.mapper.log");
        return config;
    }

}
