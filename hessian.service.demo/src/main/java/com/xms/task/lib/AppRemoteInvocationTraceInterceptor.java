package com.xms.task.lib;

import com.caucho.services.server.ServiceContext;

import com.xms.task.code.Code;
import com.xms.task.config.Constants;
import com.xms.task.exception.BizException;
import com.xms.task.exception.ServiceCheckException;
import com.xms.task.exception.ServiceInternalException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AppRemoteInvocationTraceInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AppRemoteInvocationTraceInterceptor.class);

    private final String exporterNameClause;

    public AppRemoteInvocationTraceInterceptor() {
        this.exporterNameClause = "";
    }

    public AppRemoteInvocationTraceInterceptor(String exporterName) {
        this.exporterNameClause = exporterName + " ";
    }

    public Object invoke(MethodInvocation invocation) throws Throwable, BizException {

        checkAuth();

        Method method = invocation.getMethod();

        try {
            return invocation.proceed();
        } catch (ServiceCheckException ex) {
            throw ex;
        } catch (ServiceInternalException ex) {
            // 业务层会记录
            throw ex;
        } catch (Exception ex) {
            logger.error(Constants.MARKER_INT, "Processing of " + this.exporterNameClause + "remote call resulted in fatal exception: " + ClassUtils.getQualifiedMethodName(method), ex);
            throw new ServiceInternalException(Code.SERVER_ERROR, ex);
        }
    }

    private void checkAuth() throws ServiceCheckException {

        final String authorization = ((HttpServletRequest) ServiceContext.getContextRequest()).getHeader("Authorization");

        final String serviceName = ServiceContext.getContextServiceName();

        if (authorization == null) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }

        String[] splits = authorization.split("\\:");

        if (splits.length < 3) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }

        final String time = splits[0];
        final String appId = splits[1];
        if (time == null || appId == null) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }
        final String token = authorization.substring(time.length() + appId.length() + 2);
        if (token == null) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }
        boolean expire = false;
        try {
            if ((System.currentTimeMillis() - Long.parseLong(time)) > Constants.TOKEN_EXPIRE) {
                expire = true;
            }
        } catch (Exception ex) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }

        if (expire) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }

        final String key = Constants.APPS.get(appId);

        if (key == null) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }

        StringBuilder builder = new StringBuilder();

        builder.append(time);
        builder.append(key);
        builder.append(serviceName);

        if (!token.equals(DigestUtils.sha256Hex(builder.toString()))) {
            throw new ServiceCheckException(Code.REQUEST_REFUSE);
        }
    }
}
