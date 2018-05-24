package com.bon.wx.domain.vo;

import java.io.Serializable;

/**
 * @program: dubbo-wxmanage
 * @description: 用户返回信息
 * @author: Bon
 * @create: 2018-05-24 17:05
 **/
public class UserVO implements Serializable{
    private Long userId;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private String username;

    private String remark;

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
