package com.jasper.munselfservice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SpringContext implements ApplicationContextAware {
    public static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Logger.getLogger("SpringContext").info("Setting application context.");
        APPLICATION_CONTEXT = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static RedisTemplate<Integer, String> getAutowiredRedisTemplate() {
        return (RedisTemplate<Integer, String>) getAutowireable("redisTemplate");
    }

    /**
     * Get a resource that could be autowired by `@Resource`.
     * @param beanName
     * @return
     */
    public static Object getAutowireable(String beanName) {
        return APPLICATION_CONTEXT.getBean(beanName);
    }
}
