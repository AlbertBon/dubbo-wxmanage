package com.bon.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.bon.api.IUserService;
import com.bon.api.exception.BusinessException;
import com.bon.common.constant.ExceptionType;
import com.bon.common.enumm.RetEnum;
import com.bon.common.util.MyLog;
import com.bon.dao.UserMapper;
import com.bon.model.User;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByPhone(String phone) {
        return null;
    }

    @Override
    public User findById(Long id){
        if(id.equals(1L)){
            _log.info("ceshi{}","123");
            throw new BusinessException(ExceptionType.DATA_ERROR.getCode(),ExceptionType.DATA_ERROR.getMessage());
        }
        User user = userMapper.selectByPrimaryKey(id);
        return user;
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
