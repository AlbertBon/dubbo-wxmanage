package com.bon.wx.dao;

import com.bon.wx.domain.entity.RolePermission;
import tk.mybatis.mapper.common.Mapper;

public interface RolePermissionMapper extends Mapper<RolePermission> {
    RolePermission getById(Long rolePermissionId);
}