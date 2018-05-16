package com.bon.common.config;

/**
 * @program: dubbo-wxmanage
 * @description: 常量
 * @author: Bon
 * @create: 2018-05-15 12:05
 **/
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 2;

    /**
     * token有效期（分钟）
     */
    public static final int TOKEN_EXPIRES_SECONDS = 2 * 60;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

}
