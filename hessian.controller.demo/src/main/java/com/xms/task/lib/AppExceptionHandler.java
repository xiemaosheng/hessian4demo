package com.xms.task.lib;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AppExceptionHandler implements AsyncUncaughtExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.error("method: {}, params: {}, cause: {} ", new Object[] { method.getName(), params, Throwables.getStackTraceAsString(ex) });
    }

}