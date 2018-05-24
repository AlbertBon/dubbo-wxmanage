package com.bon.common.domain.vo;

import java.io.Serializable;

/**
 * @program: dubbo-wxmanage
 * @description: token结果信息
 * @author: Bon
 * @create: 2018-05-23 17:58
 **/
public class TokenVO implements Serializable{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
