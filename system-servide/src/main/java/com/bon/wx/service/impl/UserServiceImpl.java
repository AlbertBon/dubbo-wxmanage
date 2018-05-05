package com.bon.wx.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bon.common.domain.base.ExceptionType;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MyLog;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:00
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final MyLog _log = MyLog.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByPhone(String phone) {
        return null;
    }

    @Override
    public User findById(Long id) {
        if (id.equals(1L)) {
            _log.info("ceshi{}", "123");
            throw new BusinessException(ExceptionType.DATA_ERROR.getCode(), ExceptionType.DATA_ERROR.getMessage());
        }
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void save(UserDTO userDTO){
        User user = new User();
        BeanUtil.copyPropertys(userDTO, user);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public PageInfo<User> findAll(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public String findAvatarById(Long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void resetPassword(Long id, String newPassword) {

    }

    @Override
    public List<Long> findAllUserIds() {
        return null;
    }
}
