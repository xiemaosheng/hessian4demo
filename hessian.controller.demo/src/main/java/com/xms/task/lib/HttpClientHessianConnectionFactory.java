package com.xms.task.lib;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpClientHessianConnectionFactory implements HessianConnectionFactory {

    private HttpClient httpClient;

    private HessianProxyFactory _proxyFactory;

    public void setHessianProxyFactory(HessianProxyFactory factory) {
        _proxyFactory = factory;
    }

    public HttpClientHessianConnectionFactory(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HessianConnection open(URL url) throws IOException {
        URI uri = null;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            String msg = String.format("Unable to convert URL '%s' to URI.", url.toString());
            throw new IllegalStateException(msg, e);
        }

        return new HttpClientHessianConnection(httpClient, uri);
    }

}
