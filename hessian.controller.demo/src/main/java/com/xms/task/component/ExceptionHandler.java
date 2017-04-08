package com.xms.task.component;


import com.xms.task.exception.BizException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bowen on 2017/2/23 0023.
 * 统一异常拦截
 *
 * @author bowen
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        // 拦截 BizException 转换为 wafException
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            throw bizException;
        } else {
            return null;
        }
    }
}
