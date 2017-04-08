package com.xms.task.component;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.xms.task.lib.ResponseErrorHandler;
import org.apache.http.client.HttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestRequest extends RestTemplate {

    @Resource
    private HttpClient httpClient;

    @PostConstruct
    public void init() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        setRequestFactory(factory);

        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new FastJsonHttpMessageConverter4());

        setMessageConverters(converters);

        setErrorHandler(new ResponseErrorHandler());

    }

}
