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

        public static final String TOKEN_USERNAME_TOKEN = "token_username_{0}_token_{1}";

        public static final String USER_VALIDATE_CODE_SESSION_ID = "user_validate_code_session_id_{0}";
        public static final String USER_LOGIN_USERNAME_SESSION_ID = "user_login_username_{0}_session_id_{1}";

    }

}
