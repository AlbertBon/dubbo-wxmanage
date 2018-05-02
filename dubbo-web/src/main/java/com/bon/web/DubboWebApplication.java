package com.bon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类描述信息
 * com.bon.web
 *
 * @author pengwen
 * @create 2018/4/28 0028
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.bon.api","com.bon.web"})
public class DubboWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboWebApplication.class);
    }
}
