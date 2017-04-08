package com.xms.task.lib;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface Remote {

    Class<?> serviceInterface();
}