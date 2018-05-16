package com.bon.web.controller;

import com.bon.common.domain.vo.ResultBody;
import com.bon.common.util.ImageCodeUtil;
import com.bon.wx.domain.entity.User;
import com.bon.wx.domain.vo.LoginVO;
import com.bon.wx.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: dubbo-wxmanage
 * @description: 登录
 * @author: Bon
 * @create: 2018-05-16 11:10
 **/
@RestController
@Api(value = "登录管理模块")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/loginIn",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody loginIn(@RequestParam String username, @RequestParam String password) {
        LoginVO loginVO=loginService.loginIn();
        return new ResultBody(loginVO);
//        User user = userRepository.findByUsername(username);
//        if (user == null ||  //未注册
//                !user.getPassword().equals(password)) {  //密码错误
//            //提示用户名或密码错误
//            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
//        }
//        //生成一个token，保存用户登录状态
//        TokenModel model = tokenManager.createToken(user.getId());
//        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/getImageCode")
    public ResultBody getImageCode(HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);


        ImageCodeUtil vCode = new ImageCodeUtil(120,40,4,100);
//        session.setAttribute("code", vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }




}
