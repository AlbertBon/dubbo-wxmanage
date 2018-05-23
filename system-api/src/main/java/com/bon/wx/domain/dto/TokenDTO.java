package com.bon.wx.domain.dto;

import com.bon.common.domain.dto.BaseDTO;

/**
 * @program: dubbo-wxmanage
 * @description: token参数
 * @author: Bon
 * @create: 2018-05-23 17:51
 **/
public class TokenDTO extends BaseDTO{
    private String wxOpenid;
    private String accessKey;
    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getWxOpenid() {

        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }
}
