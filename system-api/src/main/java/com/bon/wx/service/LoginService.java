package com.bon.wx.service;

import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.LoginVO;

/**
 * @program: dubbo-wxmanage
 * @description: 登录模块
 * @author: Bon
 * @create: 2018-05-16 11:14
 **/
public interface LoginService {
    LoginVO loginIn();
}
