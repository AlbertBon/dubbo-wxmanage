package com.bon.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bon.common.domain.base.ResultBody;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:16
 **/
@Api("用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @ApiResponse(code = 200, message = "success")
    @GetMapping("/query")
    public ResultBody findByKey(@RequestParam Long key){
        User user= userService.findById(key);
        return new ResultBody(user);
    }

    @ApiOperation(value = "新增用户")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody UserDTO user){
        userService.save(user);
        return new ResultBody().toJsonString();
    }
}
