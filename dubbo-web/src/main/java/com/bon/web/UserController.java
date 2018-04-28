package com.bon.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bon.api.IUserService;
import com.bon.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:16
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private IUserService userService;

    @GetMapping("/query")
    public String findByKey(@RequestParam Long key){
        User user= userService.findById(key);
        return user.toString();
    }
}
