package com.xms.task.service;

import com.xms.task.entity.User;
import com.xms.task.service.dispatcher.ExecuteDispatcher;
import com.xms.task.service.dispatcher.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Component("exampleExecutorService")
public class ExampleExecutorService implements Executor<Collection<User>,Object> {
    private ApplicationContext ctx;
    private ExecuteDispatcher<String,Collection<User>,Object> executeDispatcher;

    @Autowired
    ExampleExecutorService(ApplicationContext ctx,ExecuteDispatcher<String,Collection<User>,Object> executeDispatcher){
        Assert.notNull(executeDispatcher);
        this.ctx = ctx;
        this.executeDispatcher = executeDispatcher;
    }

    @PostConstruct
    public void registerService(){
        Executor<Collection<User>,Object> executor = (Executor<Collection<User>, Object>) ctx.getBean("exampleExecutorService");
        executeDispatcher.registerExecutor("EXAMPLE",executor);
    }

    @Override
    public Object executo(Collection<User> arg) throws Exception {
        // 业务逻辑
        return null;
    }
}
