package com.xms.task.service.dispatcher;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public interface ExecuteDispatcher<K,V,R> {
    void registerExecutor(K id,Executor<V,R> executor);

    Executor<V,R> getExecutor(K id);

    R executor(K id, V data) throws Exception;
}
