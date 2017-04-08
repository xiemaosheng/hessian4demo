package com.xms.task.controller;

import com.xms.task.api.HelloService;
import com.xms.task.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@Controller
@RequestMapping("/v0.1/hello")
public class HelloController {

    @Resource
    private HelloService helloService;

    @RequestMapping(value = "/test", name = "测试Hessian框架", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public void test(HttpServletResponse response) throws IOException{
        PrintWriter writer = response.getWriter();
        User user = new User();
        user.setUsername("xiemoasheng");
        writer.flush();
        writer.write(user.getUsername());
        writer.close();
    }
}
