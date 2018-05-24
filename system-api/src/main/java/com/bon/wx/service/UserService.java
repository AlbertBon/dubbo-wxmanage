package com.bon.wx.service;

import com.bon.common.domain.vo.PageVO;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.UserVO;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 17:47
 **/
public interface UserService {
    UserVO getById(Long id);
    void save(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(Long id);
    PageVO list(UserListDTO userListDTO);
}
