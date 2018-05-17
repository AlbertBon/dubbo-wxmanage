package com.bon.wx.service.impl;

import com.bon.common.service.RedisService;
import com.bon.wx.dao.UserBaseMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.LoginDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.LoginVO;
import com.bon.wx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @program: dubbo-wxmanage
 * @description: 登录模块实现类
 * @author: Bon
 * @create: 2018-05-16 16:37
 **/
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginVO loginIn(LoginDTO loginDTO) {
        Example example = loginDTO.createExample(new User(),"username",loginDTO.getUsername());
        User user = userBaseMapper.selectOneByExample(example);
        if(user == null || user.getPassword().equals(loginDTO.getPassword())){
//            redisService.set("")
        }
        return null;
    }
}
