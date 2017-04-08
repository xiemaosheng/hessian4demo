package com.xms.task.lib;

import com.caucho.hessian.client.HessianProxyFactory;

import com.xms.task.config.Constants;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;

public class AppHessianProxyFactory extends HessianProxyFactory {

    private String url;

    public Object create(Class<?> api, URL url, ClassLoader loader) {
        this.url = url.getPath();
        return super.create(api, url, loader);
    }

    @Override
    public String getBasicAuth() {

        String now = String.valueOf(System.currentTimeMillis());
        return now + ":" + Constants.SERVICE_APPID + ":" + DigestUtils.sha256Hex(now + Constants.SERVICE_KEY + url);
    }
}
