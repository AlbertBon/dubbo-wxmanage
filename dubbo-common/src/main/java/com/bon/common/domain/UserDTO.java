package com.bon.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: dubbo-wxmanage
 * @description: 用户数据传输对象
 * @author: Bon
 * @create: 2018-05-03 18:36
 **/
@ApiModel(value ="UserDTO")
public class UserDTO{
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "地址")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
