package com.bon.wx.service.impl;

import com.bon.common.domain.base.ExceptionType;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MyLog;
import com.bon.wx.dao.UserBaseMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Override
    public User getById(Long id) {
        if (id.equals(1L)) {
            throw new BusinessException(ExceptionType.SYSTEM_ERROR);
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
    public void update(UserDTO userDTO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PageVO listAll(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
//        PageVO pageVO = (PageVO) userMapper.listAll();
//        PageVO pageVO = new PageVO(userMapper.listAll());
        List<User> list = userBaseMapper.selectAll();
        PageVO pageVO = new PageVO(list);
        return pageVO;
    }

    @Override
    public PageVO list(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
        PageVO<User> pageVO = (PageVO<User>) userMapper.list();
        return pageVO;
    }

}
