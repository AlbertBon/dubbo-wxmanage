package com.bon.web.config;

import com.bon.web.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: dubbo-wxmanage
 * @description: config配置
 * @author: Bon
 * @create: 2018-05-10 15:38
 **/
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    //关键，将拦截器作为bean写入配置中
    @Bean
    public MyInterceptor myInterceptor(){
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources").excludePathPatterns("/v2/*").excludePathPatterns("/configuration/*");
        super.addInterceptors(registry);
    }

}
