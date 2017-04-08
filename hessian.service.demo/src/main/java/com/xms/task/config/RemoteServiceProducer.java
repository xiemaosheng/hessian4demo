package com.xms.task.config;

import com.caucho.hessian.io.*;
import com.xms.task.lib.AppHessianServiceExporter;
import com.xms.task.lib.Remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import java.util.Map;

@Configurable
public class RemoteServiceProducer implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public BeanNameUrlHandlerMapping getBeanNameUrlHandlerMapping() {
        return new BeanNameUrlHandlerMapping();
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        // 获取root 根上下文
        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        ConfigurableApplicationContext context = ((ConfigurableApplicationContext) event.getApplicationContext());

        // 获取远程接口注解

        Map<String, Object> maps = context.getBeansWithAnnotation(Remote.class);

        for (String key : maps.keySet()) {
            Object instance = maps.get(key);
            handleRemoteBean(context.getBeanFactory(), instance, instance.getClass().getAnnotation(Remote.class).serviceInterface());
        }
        // 注册handler
        context.getBean(BeanNameUrlHandlerMapping.class).initApplicationContext();

    }

    private void handleRemoteBean(SingletonBeanRegistry beanFactory, Object instance, Class iface) {

        HessianServiceExporter serviceExporer = new AppHessianServiceExporter();

        SerializerFactory factory = new SerializerFactory();

        factory.addFactory(new AbstractSerializerFactory() {

            @Override
            public Serializer getSerializer(Class cl) throws HessianProtocolException {
                if (cl.getName().equals("java.math.BigDecimal")) {
                    return new StringValueSerializer();
                }
                return null;
            }

            @Override
            public Deserializer getDeserializer(Class cl) throws HessianProtocolException {
                if (cl.getName().equals("java.math.BigDecimal")) {
                    return new BigDecimalDeserializer();
                }
                return null;
            }

        });

        serviceExporer.setSerializerFactory(factory);
        serviceExporer.setService(instance);
        serviceExporer.setServiceInterface(iface);
        serviceExporer.afterPropertiesSet();

        beanFactory.registerSingleton("/" + iface.getName(), serviceExporer);

        logger.debug("register bean:{}", "/" + iface.getName());

    }
}
