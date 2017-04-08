package com.xms.task.lib;


import com.xms.task.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class ScheduleErrorHandler implements ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleError(Throwable t) {
        logger.error( Constants.MARKER_INT, "Unexpected error occurred in scheduled task.", t);
    }
}