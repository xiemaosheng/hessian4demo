package com.xms.task.config;

import com.google.common.base.Throwables;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Configuration
@ComponentScan(basePackages = {"com.xms.task.component", "com.xms.task.service"})
@PropertySources(value = {@PropertySource(value = {"classpath:application.properties"})})
@EnableAsync
@EnableScheduling
public class AppConfig extends DefaultConnectionKeepAliveStrategy implements AsyncConfigurer {

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    // 保持最多的连接数
    private static final int CONNECTION_MAX_TOTAL = 50;

    // domain最多连接数
    private static final int CONNECTION_MAX_ROUTE = 10;

    @Resource
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public Executor getAsyncExecutor() {
        return (ThreadPoolTaskScheduler) getTaskScheduler();
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                logger.error("method: {}, params: {}, cause: {} ", new Object[]{method.getName(), params, Throwables.getStackTraceAsString(ex)});
            }
        };
    }

    @Bean
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("-consumer-task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
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

}
