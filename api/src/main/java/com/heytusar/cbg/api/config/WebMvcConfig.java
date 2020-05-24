package com.heytusar.cbg.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.heytusar.cbg.api.interceptor.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    private ApplicationContext appContext;
	
    @Autowired
	public WebMvcConfig(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		AuthInterceptor authInterceptor = appContext.getBean(AuthInterceptor.class);
        registry.addInterceptor(authInterceptor).addPathPatterns("/user*");;
    }
}
