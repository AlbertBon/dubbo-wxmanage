package com.bon.web;

import com.bon.common.domain.vo.ResultBody;
import com.bon.common.domain.vo.PageVO;
import com.bon.common.util.MD5Util;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.dto.UserListDTO;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/query",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody findByKey(@RequestParam Long key){
        UserVO vo= userService.getById(key);
        return new ResultBody(vo);
    }

    @ApiOperation(value = "新增用户")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/addUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody addUser(@RequestBody UserDTO user){
        userService.save(user);
        return new ResultBody();
    }

    @ApiOperation(value = "根据条件获取用户列表")
    @ApiResponse(code = 200, message = "success" )
    @PostMapping(value = "/list",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody list(@RequestBody UserListDTO listDTO){
        PageVO pageVO = userService.list(listDTO);
        return new ResultBody(pageVO);
    }

    @ApiOperation(value = "修改用户")
    @ApiResponse(code = 200, message = "success")
    @PostMapping(value = "/updateUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody updateUser(@RequestBody UserDTO dto){
        userService.update(dto);
        return new ResultBody();
    }
}
