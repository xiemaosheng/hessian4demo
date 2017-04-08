package com.xms.task.lib;

import com.alibaba.fastjson.JSON;

import com.xms.task.exception.ServiceCheckException;
import com.xms.task.exception.ServiceInternalException;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

public class AppHessianProxyFactoryBean extends HessianProxyFactoryBean {

    private final static Logger logger = LoggerFactory.getLogger(AppHessianProxyFactoryBean.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            logger.debug("service remote call method:{},args:{}", invocation.getMethod(), parse(invocation.getArguments()));
            return super.invoke(invocation);
        } catch (ServiceCheckException e) {
            logger.info("service remote call method:" + invocation.getMethod().getName() + ";args:" + parse(invocation.getArguments()) + ";name:" + parse(e.getNames()) + ";values:" + parse(e.getValues()), e);
            throw e;
        } catch (ServiceInternalException e) {
            logger.error("service remote call method:" + invocation.getMethod().getName() + ";args:" + parse(invocation.getArguments()), e);
            throw e;
        } catch (Exception e) {
            logger.error("service remote call method:" + invocation.getMethod().getName() + ";args:" + invocation.getArguments(), e);
            throw e;
        }
    }

    private String parse(Object[] args) {

        StringBuffer sb = new StringBuffer();

        if (args == null || args.length == 0) {
            return "";
        }

        for (int i = 0; i < args.length; i++) {
            sb.append(JSON.toJSONString(args[i]));
        }

        return sb.toString();
    }
}
