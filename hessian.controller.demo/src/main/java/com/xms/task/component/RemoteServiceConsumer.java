package com.xms.task.component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.*;
import com.xms.task.lib.AppHessianProxyFactory;
import com.xms.task.lib.AppHessianProxyFactoryBean;
import com.xms.task.lib.HttpClientHessianConnectionFactory;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

@Component
public class RemoteServiceConsumer implements BeanDefinitionRegistryPostProcessor {

    private static final String KEY_PREFIX = "service";

    private static final String KEY_NAMESPACE = "namespace";

    private static final String KEY_SERVICES = "services";

    private static Logger logger = LoggerFactory.getLogger(RemoteServiceConsumer.class);

    private Properties prop;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        if (prop == null) {
            prop = new Properties();
            try {
                prop.load(RemoteServiceConsumer.class.getClassLoader().getResourceAsStream("service.properties"));
            } catch (IOException ex) {
                logger.error("load service.properties failed, is really exists?", ex);
                return;
            }
        }

        String[] modules = prop.getProperty(key("modules")).split("\\,");

        if (modules == null || modules.length == 0) {
            return;
        }

        try {
            for (String module : modules) {
                final String url = prop.getProperty(key(module, "url"));
                final String namespace = prop.getProperty(key(module, KEY_NAMESPACE));
                final String[] services = prop.getProperty(key(module, KEY_SERVICES)).split("\\,");
                if (services == null || services.length == 0) {
                    break;
                }

                for (String service : services) {
                    registerRemoteBean(beanFactory, url + "/" + namespace + "." + service, Class.forName(namespace + "." + service));
                }
            }
        } catch (Exception ex) {
            logger.error("register Remote Bean failed.", ex);
        }

    }

    private static SerializerFactory serializerFactory = null;

    static {
        serializerFactory = new SerializerFactory();

        serializerFactory.addFactory(new AbstractSerializerFactory() {

            @Override
            public Serializer getSerializer(Class cl) throws HessianProtocolException {
                if (cl.isAssignableFrom(BigDecimal.class)) {
                    return new StringValueSerializer();
                }
                return null;
            }

            @Override
            public Deserializer getDeserializer(Class cl) throws HessianProtocolException {
                if (cl.isAssignableFrom(BigDecimal.class)) {
                    return new BigDecimalDeserializer();
                }
                return null;
            }

        });

    }

    private void registerRemoteBean(ConfigurableListableBeanFactory beanFactory, String url, Class iface) {

        HessianProxyFactory appProxyFactory = new AppHessianProxyFactory();
        appProxyFactory.setSerializerFactory(serializerFactory);
        HessianProxyFactoryBean bean = new AppHessianProxyFactoryBean();

        final String name = iface.getSimpleName().substring(0, 1).toLowerCase() + iface.getSimpleName().substring(1);

        bean.setConnectionFactory(new HttpClientHessianConnectionFactory(beanFactory.getBean(HttpClient.class)));
        bean.setServiceUrl(url);
        bean.setServiceInterface(iface);
        bean.setProxyFactory(appProxyFactory);
        bean.setOverloadEnabled(true);
        bean.afterPropertiesSet();
        beanFactory.registerSingleton(name, bean);

        logger.debug("register bean name: {}", name);
    }

    private String key(String... args) {

        StringBuffer key = new StringBuffer(KEY_PREFIX);
        for (String arg : args) {
            key.append(".");
            key.append(arg);
        }

        return key.toString();
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }
}