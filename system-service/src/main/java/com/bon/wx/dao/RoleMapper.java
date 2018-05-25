package com.bon.wx.dao;

import com.bon.wx.domain.entity.Role;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {
    Role getById(Long roleId);
}