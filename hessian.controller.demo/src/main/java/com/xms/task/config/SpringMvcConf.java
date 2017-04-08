package com.xms.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@Configuration
@EnableWebMvc
// 扫描控制器
//@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class))
@ComponentScan(basePackages = {"com.xms.task.controller"})
public class SpringMvcConf extends WebMvcConfigurerAdapter {
    // 配置视图解析器
    // html解析
//    @Bean
//    public ViewResolver htmlResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/view");
//        viewResolver.setPrefix(".html");
//        return viewResolver;
//    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // 静态资源处理
    // 当DispatcherServlet 接到匹配的请求，但是找不到相应的Controller，就会把这个请求返回给默认的处理（比如交给tomcat）

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
