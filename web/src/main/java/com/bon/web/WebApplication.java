package com.bon.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 类描述信息
 * com.bon.web
 *
 * @author pengwen
 * @create 2018/4/28 0028
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.bon.web"})
@EnableConfigurationProperties
@ImportResource("classpath:dubbo.xml")
public class WebApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        synchronized (WebApplication.class) {
            while (true) {
                try {
                    WebApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
}
