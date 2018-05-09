package com.bon.wx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 系统管理启动类
 * com.bon.wx.service
 *
 * @author pengwen
 * @create 2018/4/27 0027
 **/
@SpringBootApplication
@ComponentScan({"com.bon"})
public class SystemApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
        synchronized (SystemApplication.class) {
            while (true) {
                try {
                    SystemApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
