package com.bon.wx.service;

import com.bon.common.domain.vo.PageVO;
import com.bon.wx.domain.dto.*;
import com.bon.wx.domain.entity.Menu;
import com.bon.wx.domain.entity.Role;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.MenuVO;
import com.bon.wx.domain.vo.RoleVO;
import com.bon.wx.domain.vo.UserVO;

import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 17:47
 **/
public interface UserService {
    UserVO getUser(Long id);
    void saveUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    PageVO listUser(UserListDTO userListDTO);
    List<UserVO> getAllUser();

    RoleVO getRole(Long id);
    void saveRole(RoleDTO dto);
    void updateRole(RoleDTO dto);
    void deleteRole(Long id);
    PageVO listRole(RoleListDTO dto);
    List<RoleVO> getAllRole();

    MenuVO getMenu(Long id);
    void saveMenu(MenuDTO dto);
    void updateMenu(MenuDTO dto);
    void deleteMenu(Long id);
    PageVO listMenu(MenuListDTO dto);
    List<MenuVO> getAllMenu();


     /*保存用户角色*/
    void saveUserRole(List<Long> roleIds,Long userId);
    /*获取用户角色id列表*/
    List<Long> getUserRoleIds(Long userId);

}
