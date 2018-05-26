package com.bon.wx.dao;

import com.bon.wx.domain.entity.User;
import org.apache.ibatis.annotations.Param;

public interface GenerateMapper {
    /**
     * @Author: Bon
     * @Description: 创建数据库表
     * @param sql
     * @return: void
     * @Date: 2018/5/26 11:01
     */
    void createTable(@Param("sql") String sql);
    /**
     * @Author: Bon
     * @Description: 根据数据库表名和数据库名查询是否存在这张表
     * @param tableName
 * @param schema
     * @return: int
     * @Date: 2018/5/26 11:01
     */
    Integer findTable(@Param("tableName") String tableName,@Param("schema") String schema);
}