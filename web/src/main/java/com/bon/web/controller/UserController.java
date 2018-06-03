package com.bon.web;

import com.bon.common.domain.vo.ResultBody;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.MD5Util;
import com.bon.wx.domain.dto.RoleDTO;
import com.bon.wx.domain.dto.RoleListDTO;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
import com.bon.wx.domain.vo.RoleVO;
import com.bon.wx.domain.vo.UserVO;
import com.bon.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:16
 **/
@Api("用户管理模块")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @ApiResponse(code = 200, message = "success")
    @GetMapping(value = "/user/getUser")
    public ResultBody getUser(@RequestParam Long key){
        UserVO vo= userService.getUser(key);
        return new ResultBody(vo);
    }

    @ApiOperation(value = "新增用户")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/user/saveUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody saveUser(@RequestBody UserDTO user){
        userService.saveUser(user);
        return new ResultBody();
    }

    @ApiOperation(value = "根据条件获取用户列表")
    @ApiResponse(code = 200, message = "success" )
    @PostMapping(value = "/user/listUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody listUser(@RequestBody UserListDTO listDTO){
        PageVO pageVO = userService.listUser(listDTO);
        return new ResultBody(pageVO);
    }

    @ApiOperation(value = "修改用户")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/user/updateUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody updateUser(@RequestBody UserDTO dto){
        userService.updateUser(dto);
        return new ResultBody();
    }

    @ApiOperation(value = "删除用户")
    @GetMapping(value = "/user/deleteUser")
    public ResultBody deleteUser(@RequestParam Long key){
        userService.deleteUser(key);
        return new ResultBody();
    }

    @ApiOperation(value = "角色列表")
    @GetMapping(value = "/role/getRole")
    public ResultBody getRole(@RequestParam Long key){
        RoleVO vo= userService.getRole(key);
        return new ResultBody(vo);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/role/saveRole",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody saveRole(@RequestBody RoleDTO dto){
        userService.saveRole(dto);
        return new ResultBody();
    }

    @ApiOperation(value = "根据条件获取角色列表")
    @PostMapping(value = "/role/listRole",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody listRole(@RequestBody RoleListDTO dto){
        PageVO pageVO = userService.listRole(dto);
        return new ResultBody(pageVO);
    }

    @ApiOperation(value = "修改角色")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/role/updateRole",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody updateRole(@RequestBody RoleDTO dto){
        userService.updateRole(dto);
        return new ResultBody();
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/role/deleteRole")
    public ResultBody deleteRole(@RequestParam Long key){
        userService.deleteRole(key);
        return new ResultBody();
    }
}
