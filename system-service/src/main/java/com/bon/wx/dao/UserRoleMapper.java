package com.bon.wx.dao;

import com.bon.wx.domain.entity.UserRole;
import tk.mybatis.mapper.common.Mapper;

public interface UserRoleMapper extends Mapper<UserRole> {
    UserRole getById(Long userRoleId);
}