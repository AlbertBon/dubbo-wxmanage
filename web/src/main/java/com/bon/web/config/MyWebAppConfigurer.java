package com.bon.web.config;

import com.bon.web.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/*")
                .excludePathPatterns("/swagger-resources").excludePathPatterns("/v2/*").excludePathPatterns("/configuration/*");
        super.addInterceptors(registry);
    }

}
