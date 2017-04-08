package com.xms.task.component;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class HttpClientGC {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientGC.class);

    // 空闲时间
    private static final int IDLE_TIMEOUT = 30 * 1000;

    @Resource
    private PoolingHttpClientConnectionManager connectionManager;

    @Resource
    private HttpClient httpClient;

    @Scheduled(fixedDelay = 1000 * 60, initialDelay = 1000 * 60)
    public void gc() {
        if (connectionManager == null) {
            return;

        }
        connectionManager.closeExpiredConnections();
        connectionManager.closeIdleConnections(IDLE_TIMEOUT, TimeUnit.SECONDS);


    }

}
