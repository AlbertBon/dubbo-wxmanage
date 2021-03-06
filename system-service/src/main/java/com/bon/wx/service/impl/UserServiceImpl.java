package com.bon.wx.service.impl;

import com.bon.common.domain.dto.BaseDTO;
import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.BeanUtil;
import com.bon.common.util.MD5Util;
import com.bon.common.util.MyLog;
import com.bon.common.util.StringUtils;
import com.bon.wx.dao.*;
import com.bon.wx.domain.dto.*;
import com.bon.wx.domain.entity.*;
import com.bon.wx.domain.enums.PermissionType;
import com.bon.wx.domain.vo.MenuRouterVO;
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

    @Autowired
    private MenuExtendMapper menuExtendMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 用户
     * @param id
     * @return
     */
    @Override
    public UserVO getUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(ExceptionType.USERNAME_NULL_PASSWORD_ERROR);
        }
        UserVO vo = new UserVO();
        vo = BeanUtil.copyPropertys(user, vo);
        //放入用户角色id列表信息
        vo.setRoleIds(getUserRoleIds(user.getUserId()));
        return vo;
    }

    @Override
    public void saveUser(UserDTO dto) {
        if (StringUtils.isBlank(dto.getPassword())) {
            throw new BusinessException(ExceptionType.PASSWORD_NULL_ERROR);
        }

        dto.andFind("username", dto.getUsername());
        List<User> userList = userMapper.selectByExample(dto.getExample());
        if (userList.size() > 0) {
            throw new BusinessException("用户名重复");
        }

        dto.setPassword(MD5Util.encode(dto.getPassword()));
        User user = new User();
        BeanUtil.copyPropertys(dto, user);
        user.setUserId(null);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userMapper.insertSelective(user);
        //保存用户角色
        saveUserRole(dto.getRoleIds(), user.getUserId());
    }

    @Override
    public void updateUser(UserDTO dto) {
        if (StringUtils.isNotBlank(dto.getPassword())) {
            dto.setPassword(MD5Util.encode(dto.getPassword()));
        } else {
            dto.setPassword(null);
        }

        User user = userMapper.selectByPrimaryKey(dto.getUserId());
        if (user == null) {
            throw new BusinessException(ExceptionType.USERNAME_NULL_PASSWORD_ERROR);
        }
        user = BeanUtil.copyPropertys(dto, user);
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        //保存用户角色
        saveUserRole(dto.getRoleIds(), user.getUserId());
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
        BaseDTO dto = new BaseDTO();
        dto.andFind(new UserRole(),"userId",id+"");
        userRoleMapper.deleteByExample(dto.getExample());
    }

    @Override
    public PageVO listUser(UserListDTO userListDTO) {
        PageHelper.startPage(userListDTO);
        List<User> list = userMapper.selectByExample(userListDTO.createExample());
        PageVO pageVO = new PageVO(list);
        List<UserVO> voList = new ArrayList<>();
        for (User user : list) {
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user, vo);
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
        for (User user : list) {
            UserVO vo = new UserVO();
            BeanUtil.copyPropertys(user, vo);
            //放入用户角色id列表信息
            vo.setRoleIds(getUserRoleIds(user.getUserId()));
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 角色
     * @param id
     * @return
     */

    @Override
    public RoleVO getRole(Long id) {
        Role role = roleMapper.getById(id);
        RoleVO vo = new RoleVO();
        BeanUtil.copyPropertys(role, vo);
        //放入角色菜单id列表信息
        vo.setMenuIds(getRoleMenuIds(id));
        return vo;
    }

    @Override
    public void saveRole(RoleDTO dto) {
        Role role = new Role();
        role.setRoleId(null);
        role.setGmtCreate(new Date());
        role.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto, role);
        roleMapper.insertSelective(role);
        //保存角色菜单权限
        saveRoleMenu(dto.getMenuIds(),dto.getRoleId());
    }

    @Override
    public void updateRole(RoleDTO dto) {
        Role role = roleMapper.getById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("获取角色失败");
        }
        role.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto, role);
        roleMapper.updateByPrimaryKeySelective(role);
        //保存角色菜单权限
        saveRoleMenu(dto.getMenuIds(),dto.getRoleId());
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
        for (Role role : list) {
            RoleVO vo = new RoleVO();
            BeanUtil.copyPropertys(role, vo);
            //放入角色菜单id列表信息
            vo.setMenuIds(getRoleMenuIds(role.getRoleId()));
            voList.add(vo);
        }
        pageVO.setList(voList);
        return pageVO;
    }

    @Override
    public List<RoleVO> getAllRole() {
        List<Role> list = roleMapper.selectAll();
        List<RoleVO> voList = new ArrayList<>();
        for (Role role : list) {
            RoleVO vo = new RoleVO();
            BeanUtil.copyPropertys(role, vo);
            //放入角色菜单id列表信息
            vo.setMenuIds(getRoleMenuIds(role.getRoleId()));
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 菜单
     * @param id
     * @return
     */

    @Override
    public MenuVO getMenu(Long id) {
        Menu menu = menuMapper.getById(id);
        MenuVO vo = new MenuVO();
        BeanUtil.copyPropertys(menu, vo);
        if(menu.getParent()!=0){
            vo.setParentName(menuMapper.getById(menu.getParent()).getName());
        }
        return vo;
    }

    @Override
    public void saveMenu(MenuDTO dto) {
        Menu menu = new Menu();
        BeanUtil.copyPropertys(dto, menu);
        menu.setMenuId(null);
        menu.setGmtCreate(new Date());
        menu.setGmtModified(new Date());
        menuMapper.insertSelective(menu);
        //添加数据库id路径,如果不为空则有父节点
        if (dto.getMenuId() != null) {
            Menu m = menuMapper.selectByPrimaryKey(dto.getMenuId());
            menu.setDataPath(m.getDataPath() + "/" + menu.getMenuId());
            menu.setParent(m.getMenuId());
        } else {
            menu.setDataPath(menu.getDataPath()+"");
            menu.setParent(0L);
        }
        menuMapper.updateByPrimaryKey(menu);
        //权限表中新增菜单权限记录
        Permission permission = new Permission();
        permission.setGmtCreate(new Date());
        permission.setGmtModified(new Date());
        permission.setPermissionName("【" + PermissionType.MENU.getValue() + "】" + menu.getName());
        permission.setType(PermissionType.MENU.getKey());
        permission.setObjectId(menu.getMenuId());
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void updateMenu(MenuDTO dto) {
        Menu menu = menuMapper.selectByPrimaryKey(dto.getMenuId());
        if (menu == null) {
            throw new BusinessException("获取菜单失败");
        }
        menu.setGmtModified(new Date());
        BeanUtil.copyPropertys(dto, menu);
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        menuMapper.deleteByPrimaryKey(menuId);
        //删除权限表对应记录
        BaseDTO dto = new BaseDTO();
        dto.createExample(new Permission());
        dto.andFind("type", PermissionType.MENU.getKey());
        dto.andFind("objectId", String.valueOf(menuId));
        permissionMapper.deleteByExample(dto.getExample());
    }

    @Override
    public PageVO listMenu(MenuListDTO dto) {
        PageHelper.startPage(dto);
        List<Menu> list = menuMapper.selectByExample(dto.createExample());
        PageVO pageVO = new PageVO(list);
        List<MenuVO> voList = new ArrayList<>();
        for (Menu menu : list) {
            MenuVO vo = new MenuVO();
            BeanUtil.copyPropertys(menu, vo);
            if(menu.getParent()!=0){
                vo.setParentName(menuMapper.getById(menu.getParent()).getName());
            }
            voList.add(vo);
        }
        pageVO.setList(voList);
        return pageVO;
    }

    @Override
    public List<MenuVO> getAllMenu() {
        List<Menu> list = menuMapper.selectAll();
        List<MenuVO> voList = new ArrayList<>();
        for (Menu menu : list) {
            MenuVO vo = new MenuVO();
            BeanUtil.copyPropertys(menu, vo);
            //只获取子菜单
            if(vo.getParent()!=0){
                voList.add(vo);
            }
        }
        return voList;
    }

    @Override
    public void saveUserRole(List<Long> roleIds, Long userId) {
        //删除用户角色
        BaseDTO<UserRole> dto = new BaseDTO();
        dto.andFind(new UserRole(), "userId", userId + "");
        userRoleMapper.deleteByExample(dto.getExample());
        //插入角色
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setGmtCreate(new Date());
            userRole.setGmtModified(new Date());
            userRoleMapper.insertSelective(userRole);
        }
    }

    @Override
    public void saveRoleMenu(List<Long> menuIds, Long roleId) {
        //删除角色菜单权限
        BaseDTO dto = new BaseDTO();
        dto.andFind(new RolePermission(), "roleId", roleId + "");
        rolePermissionMapper.deleteByExample(dto.getExample());
        List<Long> rootIds=new ArrayList<>();
        //插入菜单权限id
        for (Long menuId : menuIds) {
            BaseDTO dto1 = new BaseDTO();
            dto1.andFind(new Permission(),"objectId",menuId+"");
            dto1.andFind("type",PermissionType.MENU.getKey());
            Permission permission = permissionMapper.selectOneByExample(dto1.getExample());
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permission.getPermissionId());
            rolePermission.setRoleId(roleId);
            rolePermission.setGmtCreate(new Date());
            rolePermission.setGmtModified(new Date());
            rolePermissionMapper.insertSelective(rolePermission);
            //如果没有根节点则插入根节点id
            Menu menu = menuMapper.getById(menuId);
            Long rootId = Long.valueOf(menu.getDataPath().split("/")[0]);
            if(!rootIds.contains(rootId)){
                rootIds.add(rootId);
                rolePermission = new RolePermission();
                rolePermission.setPermissionId(rootId);
                rolePermission.setRoleId(roleId);
                rolePermission.setGmtCreate(new Date());
                rolePermission.setGmtModified(new Date());
                rolePermissionMapper.insertSelective(rolePermission);
            }
        }
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        //查找用户所有角色
        BaseDTO dto = new BaseDTO();
        dto.andFind(new UserRole(), "userId", userId + "");
        List<UserRole> userRoleList = userRoleMapper.selectByExample(dto.getExample());
        List<Long> voList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            voList.add(userRole.getRoleId());
        }
        return voList;
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        //查找角色所有菜单
        List<Long> voList = menuExtendMapper.getIdsByRoleId(roleId);
        return voList;
    }

    @Override
    public List<MenuRouterVO> getMenuRouterByRole(Long roleId) {
        List<Menu> menuList = menuExtendMapper.getByRoleId(roleId);
        List<MenuRouterVO> voList = funMenuChildByRole(menuList,roleId);
        return voList;
    }

    @Override
    public List<MenuRouterVO> getMenuRouterByUser(Long userId) {
        List<Menu> menuList = menuExtendMapper.getByUserId(userId);
        List<MenuRouterVO> voList = funMenuChildByUserId(menuList,userId);
        return voList;
    }


    @Override
    public List<MenuRouterVO> getAllMenuRouter() {
        BaseDTO dto = new BaseDTO();
        dto.andFind(new Menu(), "parent", 0+"");
        List<Menu> menuList = menuMapper.selectByExample(dto.getExample());
        List<MenuRouterVO> voList = funMenuChildAll(menuList);
        return voList;
    }

    /**
     * @Author: Bon
     * @Description: 递归获取子菜单(根据userId)
     * @param menuList
     * @return: java.util.List<com.bon.wx.domain.vo.MenuRouterVO>
     * @Date: 2018/6/6 15:04
     */
    public List<MenuRouterVO> funMenuChildAll(List<Menu> menuList) {
        List<MenuRouterVO> voList = new ArrayList<>();
        for (Menu menu : menuList) {
            //转化为路由菜单
            MenuRouterVO vo = new MenuRouterVO();
            MenuRouterVO.Meta meta = vo.new Meta();
            vo.setAlwaysShow(menu.getAlwaysShow());
            vo.setComponent(menu.getComponent());
            vo.setHidden(menu.getHidden());
            vo.setName(menu.getName());
            vo.setPath(menu.getPath());
            meta.setIcon(menu.getIcon());
            meta.setTitle(menu.getTitle());
            vo.setMeta(meta);

            //判断如果还有子菜单就递归调用继续查找子菜单
            BaseDTO dto = new BaseDTO();
            dto.likeFind(new Menu(),"dataPath",menu.getDataPath()+"/%");
            List<Menu> menuList1 = menuMapper.selectByExample(dto.getExample());
            if (menuList1.size() > 0) {
                vo.setChildren(funMenuChildAll(menuList1));
            }
            voList.add(vo);
        }
        return voList;
    }

    /**
     * @Author: Bon
     * @Description: 递归获取子菜单(根据userId)
     * @param menuList
     * @return: java.util.List<com.bon.wx.domain.vo.MenuRouterVO>
     * @Date: 2018/6/6 15:04
     */
    public List<MenuRouterVO> funMenuChildByUserId(List<Menu> menuList,Long userId) {
        List<MenuRouterVO> voList = new ArrayList<>();
        for (Menu menu : menuList) {
            //转化为路由菜单
            MenuRouterVO vo = new MenuRouterVO();
            MenuRouterVO.Meta meta = vo.new Meta();
            vo.setAlwaysShow(menu.getAlwaysShow());
            vo.setComponent(menu.getComponent());
            vo.setHidden(menu.getHidden());
            vo.setName(menu.getName());
            vo.setPath(menu.getPath());
            meta.setIcon(menu.getIcon());
            meta.setTitle(menu.getTitle());
            vo.setMeta(meta);

            //判断如果还有子菜单就递归调用继续查找子菜单
            List<Menu> menuList1 = menuExtendMapper.getByPathAndUserId(menu.getDataPath(),userId);
            if (menuList1.size() > 0) {
                vo.setChildren(funMenuChildByUserId(menuList1,userId));
            }
            voList.add(vo);
        }
        return voList;
    }
    /**
     * @Author: Bon
     * @Description: 递归获取子菜单(根据roleId获取)
     * @param menuList
     * @return: java.util.List<com.bon.wx.domain.vo.MenuRouterVO>
     * @Date: 2018/6/6 15:04
     */
    public List<MenuRouterVO> funMenuChildByRole(List<Menu> menuList,Long roleId) {
        List<MenuRouterVO> voList = new ArrayList<>();
        for (Menu menu : menuList) {
            //转化为路由菜单
            MenuRouterVO vo = new MenuRouterVO();
            MenuRouterVO.Meta meta = vo.new Meta();
            vo.setAlwaysShow(menu.getAlwaysShow());
            vo.setComponent(menu.getComponent());
            vo.setHidden(menu.getHidden());
            vo.setName(menu.getName());
            vo.setPath(menu.getPath());
            meta.setIcon(menu.getIcon());
            meta.setTitle(menu.getTitle());
            vo.setMeta(meta);

            //判断如果还有子菜单就递归调用继续查找子菜单
            List<Menu> menuList1 = menuExtendMapper.getByPathAndRoleId(menu.getDataPath(),roleId);
            if (menuList1.size() > 0) {
                vo.setChildren(funMenuChildByRole(menuList1,roleId));
            }
            voList.add(vo);
        }
        return voList;
    }

}
