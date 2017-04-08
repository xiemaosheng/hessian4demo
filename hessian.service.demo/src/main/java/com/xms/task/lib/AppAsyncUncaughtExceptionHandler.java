package com.xms.task.lib;

import com.google.common.base.Throwables;
import com.xms.task.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AppAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        logger.error(Constants.MARKER_INT, "method: {}, params: {}, cause: {} ", new Object[] { method.getName(), params, Throwables.getStackTraceAsString(ex) });

    }

}
