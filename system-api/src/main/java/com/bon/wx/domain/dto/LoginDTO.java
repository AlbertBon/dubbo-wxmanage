package com.bon.wx.domain.dto;

/**
 * @program: dubbo-wxmanage
 * @description: 登录参数
 * @author: Bon
 * @create: 2018-05-16 11:16
 **/
public class LoginDTO {
    private String username;
    private String password;
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
