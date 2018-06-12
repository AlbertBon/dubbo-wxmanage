package com.bon.wx.dao;

import com.bon.wx.domain.entity.Menu;
import com.bon.wx.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuExtendMapper {
    /**
     * 根据id获取视图
     * @param menuId
     * @return
     */
    MenuVO getById(Long menuId);

    /**
     * 根据用户id获取权限根菜单列表
     * @param userId
     * @return
     */
    List<Menu> getByUserId(Long userId);

    /**
     * 根据父路径和用户id获取权限子菜单
     * @param path
     * @param userId
     * @return
     */
    List<Menu> getByPathAndUserId(@Param("path") String path, @Param("userId") Long userId);

    /**
     * 根据角色id获取权限根菜单列表
     * @param roleId
     * @return
     */
    List<Menu> getByRoleId(Long roleId);

    /**
     * 根据父路径和角色id获取权限子菜单
     * @param path
     * @param roleId
     * @return
     */
    List<Menu> getByPathAndRoleId(@Param("path") String path, @Param("roleId") Long roleId);

    /**
     * 根据角色id获取权限子菜单id
     * @param path
     * @param roleId
     * @return
     */
    List<Long> getIdsByRoleId(Long roleId);
}