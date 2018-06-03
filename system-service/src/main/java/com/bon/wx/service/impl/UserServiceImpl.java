package com.bon.wx.service.impl;

import com.bon.common.domain.dto.BaseDTO;
import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MD5Util;
import com.bon.common.util.MyLog;
import com.bon.common.util.StringUtils;
import com.bon.wx.dao.MenuMapper;
import com.bon.wx.dao.RoleMapper;
import com.bon.wx.dao.UserMapper;
import com.bon.wx.dao.UserRoleMapper;
import com.bon.wx.domain.dto.*;
import com.bon.wx.domain.entity.Menu;
import com.bon.wx.domain.entity.Role;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.entity.UserRole;
import com.bon.wx.domain.vo.MenuVO;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户信息管理模块
 * @author: Bon
 * @create: 2018-04-27 18:00
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final MyLog LOG = MyLog.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserVO getUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new BusinessException(ExceptionType.USERNAME_NULL_PASSWORD_ERROR);
        }
        UserVO vo = new UserVO();
        vo = BeanUtil.copyPropertys(user,vo);
        //放入用户角色id列表信息
        vo.setRoleIds(getUserRoleIds(user.getUserId()));
        return vo;
    }

    @Override
    public void saveUser(UserDTO dto) {
        if(StringUtils.isBlank(dto.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NULL_ERROR);
        }
        dto.setPassword(MD5Util.encode(dto.getPassword()));
        User user = new User();
        BeanUtil.copyPropertys(dto, user);
        user.setUserId(null);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insertSelective(user);
        //保存用户角色
        saveUserRole(dto.getRoleIds(),user.getUserId());
    }

    @Override
    public void updateUser(UserDTO dto) {
        if(StringUtils.isNotBlank(dto.getPassword())){
            dto.setPassword(MD5Util.encode(dto.getPassword()));
        }else {
            dto.setPassword(null);
        }

        User user = userMapper.selectByPrimaryKey(dto.getUserId());
        if(user==null){
            throw new BusinessException(ExceptionType.USERNAME_NULL_PASSWORD_ERROR);
        }
        user = BeanUtil.copyPropertys(dto,user);
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        //保存用户角色
        saveUserRole(dto.getRoleIds(),user.getUserId());
    }

    @Override

    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVO listUser(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
        List<User> list = userMapper.selectByExample(userListDTO.createExample());
        PageVO pageVO = new PageVO(list);
        List<UserVO> voList = new ArrayList<>();
        for (User user : list){
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user,vo);
            //放入用户角色id列表信息
            vo.setRoleIds(getUserRoleIds(user.getUserId()));
            voList.add(vo);
        }
        pageVO.setList(voList);
        return pageVO;
    }

    @Override
    public List<UserVO> getAllUser() {
        List<User> list = userMapper.selectAll();
        List<UserVO> voList = new ArrayList<>();
        for (User user : list){
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user,vo);
            //放入用户角色id列表信息
            vo.setRoleIds(getUserRoleIds(user.getUserId()));
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public RoleVO getRole(Long id) {
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
        PageVO pageVO = new PageVO(list);
        List<RoleVO> voList = new ArrayList<>();
        for (Role role : list){
            RoleVO vo = new RoleVO();
            BeanUtil.copyPropertys(role,vo);
            voList.add(vo);
        }
        pageVO.setList(voList);
        return pageVO;
    }

    @Override
    public List<RoleVO> getAllRole() {
        List<Role> list = roleMapper.selectAll();
        List<RoleVO> voList = new ArrayList<>();
        for (Role role : list){
            RoleVO vo = new RoleVO();
            BeanUtil.copyPropertys(role,vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public MenuVO getMenu(Long id) {
        Menu menu = menuMapper.getById(id);
        MenuVO vo = new MenuVO();
        BeanUtil.copyPropertys(menu,vo);
        return vo;
    }

    @Override
    public void saveMenu(MenuDTO dto) {
        Menu menu = new Menu();
        menu.setMenuId(null);
        menu.setGmtCreate(new Date());
        menu.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto,menu);
        menuMapper.insertSelective(menu);
    }

    @Override
    public void updateMenu(MenuDTO dto) {
        Menu menu = menuMapper.getById(dto.getMenuId());
        if(menu == null){
            throw new BusinessException("获取菜单失败");
        }
        menu.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto,menu);
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVO listMenu(MenuListDTO dto) {
        PageHelper.startPage(dto);
        List<Menu> list = menuMapper.selectByExample(dto.createExample());
        PageVO pageVO = new PageVO(list);
        List<MenuVO> voList = new ArrayList<>();
        for (Menu menu : list){
            MenuVO vo = new MenuVO();
            BeanUtil.copyPropertys(menu,vo);
            voList.add(vo);
        }
        pageVO.setList(voList);
        return pageVO;
    }

    @Override
    public List<MenuVO> getAllMenu() {
        List<Menu> list = menuMapper.selectAll();
        List<MenuVO> voList = new ArrayList<>();
        for (Menu menu : list){
            MenuVO vo = new MenuVO();
            BeanUtil.copyPropertys(menu,vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void saveUserRole(List<Long> roleIds, Long userId) {
        //删除用户角色
        BaseDTO<UserRole> dto =new BaseDTO();
        dto.createExample(new UserRole());
        dto.andFind("userId",userId+"");
        userRoleMapper.deleteByExample(dto.getExample());
        //插入角色
        for (Long roleId:roleIds){
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setGmtCreate(new Date());
            userRole.setGmtModified(new Date());
            userRoleMapper.insertSelective(userRole);
        }
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        //查找用户所有角色
        BaseDTO<UserRole> dto =new BaseDTO();
        dto.createExample(new UserRole());
        dto.andFind("userId",userId+"");
        List<UserRole> userRoleList=userRoleMapper.selectByExample(dto.getExample());
        List<Long> voList=new ArrayList<>();
        for(UserRole userRole:userRoleList){
            voList.add(userRole.getRoleId());
        }
        return voList;
    }

}
