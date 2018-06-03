package com.bon.wx.dao;

import com.bon.wx.domain.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

public interface MenuMapper extends Mapper<Menu> {
    Menu getById(Long menuId);
}