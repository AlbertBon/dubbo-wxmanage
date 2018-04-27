package com.bon.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bon.api.IUserService;
import com.bon.common.util.MyLog;
import com.bon.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:00
 **/
@Service
public class UserServiceImpl implements IUserService {

    private static final MyLog _log = MyLog.getLog(UserServiceImpl.class);

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByPhone(String phone) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void save(User User) {

    }

    @Override
    public void update(User User) {

    }

    @Override
    public PageInfo<User> findAll(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public String findAvatarById(Long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void resetPassword(Long id, String newPassword) {

    }

    @Override
    public List<Long> findAllUserIds() {
        return null;
    }
}
