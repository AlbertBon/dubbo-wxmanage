package com.bon.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bon.api.IUserService;
import com.bon.api.exception.BusinessException;
import com.bon.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "用户列表")
    @ApiResponse(code = 200, message = "success")
    @GetMapping("/query")
    public String findByKey(@RequestParam Long key){
        User user= userService.findById(key);
        return user.toString();
    }
}
