package com.xms.task.service;

import com.xms.task.api.HelloService;
import com.xms.task.entity.User;
import com.xms.task.lib.Remote;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@Service
@Remote(serviceInterface = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public void say(User user) {
        System.out.println("hello world:" + user.getUsername());
    }
}
