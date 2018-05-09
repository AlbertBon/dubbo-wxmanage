package com.bon.wx.dao;

import com.bon.wx.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;

public interface GenerateMapper {
    void createTable(@Param("sql") String sql);
}