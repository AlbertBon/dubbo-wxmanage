package com.bon.wx.service.impl;

import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MD5Util;
import com.bon.common.util.MyLog;
import com.bon.common.util.StringUtils;
import com.bon.wx.dao.RoleMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.domain.dto.RoleDTO;
import com.bon.wx.domain.dto.RoleListDTO;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
import com.bon.wx.domain.entity.Role;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.RoleVO;
import com.bon.wx.domain.vo.UserVO;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户信息管理模块
 * @author: Bon
 * @create: 2018-04-27 18:00
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final MyLog LOG = MyLog.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserVO getById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserVO vo = new UserVO();
        vo = BeanUtil.copyPropertys(user,vo);
        return vo;
    }

    @Override
    public void save(UserDTO dto) {
        if(StringUtils.isBlank(dto.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NULL_ERROR);
        }
        dto.setPassword(MD5Util.encode(dto.getPassword()));
        User user = new User();
        user.setUserId(null);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto, user);
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public void update(UserDTO dto) {
        if(StringUtils.isNotBlank(dto.getPassword())){
            dto.setPassword(MD5Util.encode(dto.getPassword()));
        }else {
            dto.setPassword(null);
        }

//        dto.andFind("username",dto.getUsername());
//        User user=userMapper.selectOneByExample(dto.getExample());
        User user = userMapper.selectByPrimaryKey(dto.getUserId());
        if(user==null){
            throw new BusinessException(ExceptionType.USERNAME_NULL_PASSWORD_ERROR);
        }
        user = BeanUtil.copyPropertys(dto,user);
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVO list(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
        List<User> list = userMapper.selectByExample(userListDTO.createExample());
        List<UserVO> voList = new ArrayList<>();
        for (User user : list){
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user,vo);
            voList.add(vo);
        }
        PageVO pageVO = new PageVO(voList);
        return pageVO;
    }

    @Override
    public RoleVO getRoleById(Long id) {
        Role role = roleMapper.getById(id);
        RoleVO vo = new RoleVO();
        BeanUtil.copyPropertys(role,vo);
        return vo;
    }

    @Override
    public void saveRole(RoleDTO dto) {
        Role role = new Role();
        role.setRoleId(null);
        role.setGmtCreate(new Date());
        role.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto,role);
        roleMapper.insertSelective(role);
    }

    @Override
    public void updateRole(RoleDTO dto) {
        Role role = roleMapper.getById(dto.getRoleId());
        if(role == null){
            throw new BusinessException("获取角色失败");
        }
        role.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto,role);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVO listRole(RoleListDTO listDTO) {
        PageHelper.startPage(listDTO);
        List<Role> list = roleMapper.selectByExample(listDTO.createExample());
        List<RoleVO> voList = new ArrayList<>();
        for (Role role : list){
            RoleVO vo = new RoleVO();
            BeanUtil.copyPropertys(role,vo);
            voList.add(vo);
        }
        PageVO pageVO = new PageVO(voList);
        return pageVO;
    }

}
