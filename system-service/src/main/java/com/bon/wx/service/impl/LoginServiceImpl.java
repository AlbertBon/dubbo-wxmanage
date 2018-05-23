package com.bon.wx.service.impl;

import com.bon.common.config.Constants;
import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.service.RedisService;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MD5Util;
import com.bon.common.util.MyLog;
import com.bon.common.util.StringUtils;
import com.bon.wx.dao.UserBaseMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.LoginDTO;
import com.bon.wx.domain.dto.TokenDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.LoginVO;
import com.bon.wx.domain.vo.TokenVO;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: dubbo-wxmanage
 * @description: 登录模块实现类
 * @author: Bon
 * @create: 2018-05-16 16:37
 **/
@Service
public class LoginServiceImpl implements LoginService{

    private static final MyLog LOG = MyLog.getLog(LoginServiceImpl.class);

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginVO loginIn(LoginDTO loginDTO,String sessionId) {
        String vCode=redisService.get(MessageFormat.format(Constants.RedisKey.USER_VALIDATE_CODE_SESSION_ID,sessionId));
        if(!vCode.equalsIgnoreCase(loginDTO.getCode())){
            throw new BusinessException(ExceptionType.VALIDATE_CODE_ERROR);
        }

        Example example = loginDTO.createExample(new User(),"username=",loginDTO.getUsername());
        example.and().andCondition("password=", MD5Util.encode(loginDTO.getPassword()));
        User user = userBaseMapper.selectOneByExample(example);
        if(user == null ){
            throw new BusinessException(ExceptionType.USERNAME_OR_PASSWORD_ERROR);
        }

        String key= MessageFormat.format(Constants.RedisKey.USER_LOGIN_USERNAME_TIMESTAMP_SESSION_ID,user.getUsername(),new Date().getTime(),sessionId);
        redisService.create(key,user.getUsername()+"_"+new Date().getTime()+"_"+sessionId);

        LoginVO loginVO = new LoginVO();
        BeanUtil.copyPropertys(user,loginVO);
        // TODO: 2018/5/21 给登录用户添加登录信息
        return loginVO;
    }

    @Override
    public TokenVO getToken(TokenDTO dto) {
        // 使用 uuid 作为源 token
        String token = UUID.randomUUID().toString().replace("-", "");
        if(StringUtils.isNotBlank(dto.getWxOpenid())){
            Example example = dto.createExample(new User(),"wxOpenid=",dto.getWxOpenid());
            User user = userBaseMapper.selectOneByExample(example);
            if(user!=null){
                // 存储到 redis 并设置过期时间(默认2小时)
                redisService.create(MessageFormat.format(Constants.RedisKey.TOKEN_USER_ID,user.getUserId()),token);
            }
        }
        return null;
    }
}
