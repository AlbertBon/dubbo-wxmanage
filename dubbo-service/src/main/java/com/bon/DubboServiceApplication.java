package com.bon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类描述信息
 * com.bon.service
 *
 * @author pengwen
 * @create 2018/4/27 0027
 **/
@SpringBootApplication
@ComponentScan({"com.bon"})
public class DubboServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication.class,args);
    }
}
