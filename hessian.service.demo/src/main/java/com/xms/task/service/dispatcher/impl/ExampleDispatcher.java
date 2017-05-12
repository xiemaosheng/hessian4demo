package com.xms.task.service.dispatcher.impl;

import com.xms.task.entity.User;
import com.xms.task.service.dispatcher.ExecuteDispatcher;
import com.xms.task.service.dispatcher.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class ExampleDispatcher implements ExecuteDispatcher<String,Collection<User>,Object> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String,Executor<Collection<User>,Object>> executorMap = new ConcurrentHashMap<>();

    @Override
    public void registerExecutor(String id, Executor<Collection<User>, Object> executor) {
        if (executorMap.containsKey(id)){
            Executor<Collection<User>,Object> oldHandler = executorMap.get(id);
            logger.info("Executor id: {}, class: {} already exists. It will be replaced by the new one",id,oldHandler.getClass());
        }else {
            logger.info("Executor id: {}, class: {} is added to the executor map", id, executor.getClass());
        }
        executorMap.put(id,executor);
    }

    @Override
    public Executor<Collection<User>, Object> getExecutor(String id) {
        if (executorMap.containsKey(id)){
            return executorMap.get(id);
        }
        logger.info("handler id: {}'s mapping handler class is not found", id);
        return null;
    }

    @Override
    public Object executor(String id, Collection<User> data) throws Exception {
        Executor<Collection<User>,Object> executor = getExecutor(id);
        if (executor == null){
            logger.error("executor id: {} not found.", id);
            // 一般抛出异常信息
        }
        return executor.executo(data);
    }
}
