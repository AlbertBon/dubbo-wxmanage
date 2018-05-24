package com.bon.wx.service;

import com.bon.wx.domain.dto.LoginDTO;
import com.bon.wx.domain.dto.TokenDTO;
import com.bon.wx.domain.vo.LoginVO;
import com.bon.common.domain.vo.TokenVO;

/**
 * @program: dubbo-wxmanage
 * @description: 登录模块
 * @author: Bon
 * @create: 2018-05-16 11:14
 **/
public interface LoginService {
    LoginVO loginIn(LoginDTO loginDTO,String sessionId);
    TokenVO getToken(TokenDTO dto);
    boolean check(String pattern);
}
