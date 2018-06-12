package com.bon.wx.dao;

import com.bon.wx.domain.entity.Permission;
import tk.mybatis.mapper.common.Mapper;

public interface PermissionExtendMapper  {
    Permission getById(Long permissionId);
}