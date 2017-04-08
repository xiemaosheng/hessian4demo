package com.xms.task.lib;

import com.caucho.services.server.ServiceContext;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppHessianServiceExporter extends HessianServiceExporter {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceContext.begin(request, response, request.getRequestURI(), null);
        super.handleRequest(request, response);
    }

    @Override
    protected Object getProxyForService() {
        setInterceptors(new Object[] { new AppRemoteInvocationTraceInterceptor(getExporterName()) });
        return super.getProxyForService();
    }

}