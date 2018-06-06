package com.bon.wx.dao;

import com.bon.wx.domain.entity.Menu;
import com.bon.wx.domain.vo.MenuVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuExtendMapper {
    MenuVO getById(Long menuId);
    List<Menu> getByUserId(Long userId);
}