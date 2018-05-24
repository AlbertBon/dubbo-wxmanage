package com.bon.common.config;

/**
 * @program: dubbo-wxmanage
 * @description: 常量
 * @author: Bon
 * @create: 2018-05-15 12:05
 **/
public class Constants {
    /*存储当前登录用户id的字段名*/
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    /*token有效期（小时）*/
    public static final int TOKEN_EXPIRES_HOUR = 2;
    /*token有效期（分钟）*/
    public static final int TOKEN_EXPIRES_SECONDS = 2 * 60 * 60;
    /*存放Authorization的header字段*/
    public static final String AUTHORIZATION = "authorization";

    public static final class RedisKey {

        public static final String TOKEN_USERNAME_TOKEN = "TOKEN_USERNAME_TOKEN_{0}_{1}";//token session

        public static final String LOGIN_CAPTCHA_SESSION_ID = "LOGIN_CAPTCHA_SESSION_ID_{0}";//验证码
        public static final String LOGIN_USERNAME_SESSION_ID = "LOGIN_USERNAME_SESSION_ID_{0}_{1}";//用户登录session

    }

}
