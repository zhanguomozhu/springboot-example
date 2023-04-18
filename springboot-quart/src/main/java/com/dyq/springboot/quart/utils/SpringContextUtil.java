package com.dyq.springboot.quart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.websocket.server.ServerEndpointConfig;

@Configuration
@Lazy(value = false)
public class SpringContextUtil extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    Logger logger= LoggerFactory.getLogger(SpringContextUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
        logger.debug("springboot started!");
    }

    public static ApplicationContext getCtx() {
        return SpringContextUtil.applicationContext;
    }

    public static <T> T getBean(Class<T> t) {
        return SpringContextUtil.applicationContext.getBean(t);
    }
    public static Object getBean(String beanName) {
        return SpringContextUtil.applicationContext.getBean(beanName);
    }
    public static <T> T getBean(String beanName,Class<T> t ) {
        return SpringContextUtil.applicationContext.getBean(beanName,t);
    }


}