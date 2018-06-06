package com.bon.wx.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 登录信息
 * @author: Bon
 * @create: 2018-05-16 14:06
 **/
public class LoginVO implements Serializable{
    private Long userId;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private String username;

    private String remark;
    //token(uuid)
    private String token;
    //前端菜单路由
    private List<MenuRouterVO> routers;

    public List<MenuRouterVO> getRouters() {
        return routers;
    }

    public void setRouters(List<MenuRouterVO> routers) {
        this.routers = routers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
