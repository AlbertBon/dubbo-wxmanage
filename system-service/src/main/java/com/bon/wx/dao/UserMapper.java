package com.bon.wx.dao;

import com.bon.wx.domain.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getById(Long userId);
}