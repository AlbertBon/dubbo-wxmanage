package com.bon.wx.dao;

import com.bon.common.util.BaseMapper;
import com.bon.wx.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: dubbo-wxmanage
 * @description: 通用mapper
 * @author: Bon
 * @create: 2018-05-11 17:45
 **/
public interface UserBaseMapper extends BaseMapper<User> {

    List<User> listAll();
}
