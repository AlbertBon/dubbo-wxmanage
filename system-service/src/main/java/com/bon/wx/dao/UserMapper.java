package com.bon.wx.dao;

import com.bon.common.util.MyMapper;
import com.bon.wx.domain.entity.User;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface UserMapper {
//public interface UserMapper extends MyMapper<User>{
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> listAll();

    List<User> list();
}