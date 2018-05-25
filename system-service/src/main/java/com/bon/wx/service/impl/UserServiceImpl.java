package com.bon.wx.service.impl;

import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MyLog;
import com.bon.wx.dao.UserBaseMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.UserVO;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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

    private static final MyLog LOG = MyLog.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO getById(Long id) {
        if (id.equals(1L)) {
            throw new BusinessException(ExceptionType.SYSTEM_ERROR);
        }
        User user = userMapper.selectByPrimaryKey(id);
        UserVO vo = new UserVO();
        vo = BeanUtil.copyPropertys(user,vo);
        return vo;
    }

    @Override
    public void save(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyPropertys(userDTO, user);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        Example example = userDTO.createExample(new User(),"username=",userDTO.getUsername());
        User user = userMapper.selectOneByExample(example);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PageVO list(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
        List<User> list = userMapper.selectByExample(userListDTO.createExample(new User()));
        List<UserVO> voList = new ArrayList<>();
        for (User user : list){
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user,vo);
            voList.add(vo);
        }
        PageVO pageVO = new PageVO(voList);
        return pageVO;
    }

}
