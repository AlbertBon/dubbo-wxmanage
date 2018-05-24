package com.bon.wx.dao;

import com.bon.wx.domain.entity.RolePermission;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long rolePermissionId);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long rolePermissionId);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}