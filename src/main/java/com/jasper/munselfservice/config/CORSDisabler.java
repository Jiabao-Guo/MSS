package com.jasper.munselfservice.config;

import com.jasper.munselfservice.interceptor.SessionInterceptor;
import com.jasper.munselfservice.util.CORSUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Order(0)
public class CORSDisabler implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CORSUtil.addCORSDisablingMappings(registry);
    }

}
