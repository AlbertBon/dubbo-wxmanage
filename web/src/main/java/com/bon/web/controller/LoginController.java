package com.bon.web.controller;

import com.bon.common.config.Constants;
import com.bon.common.domain.vo.ResultBody;
import com.bon.common.service.RedisService;
import com.bon.common.util.ImageCodeUtil;
import com.bon.wx.domain.dto.LoginDTO;
import com.bon.wx.domain.dto.TokenDTO;
import com.bon.wx.domain.vo.LoginVO;
import com.bon.common.domain.vo.TokenVO;
import com.bon.wx.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

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

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/loginIn",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBody loginIn(@RequestBody LoginDTO loginDTO,HttpServletRequest request) {
        LoginVO loginVO=loginService.loginIn(loginDTO,request.getRequestedSessionId());
        return new ResultBody(loginVO);
    }

    @ApiOperation(value = "生成验证码")
    @GetMapping(value = "/getImageCode")
    public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ImageCodeUtil vCode = new ImageCodeUtil(120,40,4,100);

        String key= MessageFormat.format(Constants.RedisKey.USER_VALIDATE_CODE_SESSION_ID,request.getRequestedSessionId());
        redisService.create(key,vCode.getCode());
        vCode.write(response.getOutputStream());
    }

    @ApiOperation(value = "获取token")
    @GetMapping(value = "/getToken")
    public ResultBody getToken(@RequestBody TokenDTO dto) throws IOException {
        TokenVO vo = loginService.getToken(dto);
        return new ResultBody(vo);
    }


}
