package com.bon.wx.service;

import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 17:47
 **/
public interface UserService {
    User findByUsername(String username);
    User findByPhone(String phone);
    User findById(Long id);
    void save(UserDTO user);
    void update(User User);
    PageInfo<User> findAll(int pageNum, int pageSize);
    String findAvatarById(Long id);
    User findByEmail(String email);
    void resetPassword(Long id, String newPassword);
    List<Long> findAllUserIds();
}
