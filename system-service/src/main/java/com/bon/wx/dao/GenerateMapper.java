package com.bon.wx.dao;

import com.bon.wx.domain.entity.User;
import org.apache.ibatis.annotations.Param;

public interface GenerateMapper {
    void createTable(@Param("sql") String sql);
}