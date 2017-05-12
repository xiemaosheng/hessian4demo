package com.xms.task.service.dispatcher;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public interface Executor<V,R> {
    R executo(V arg) throws Exception;
}
