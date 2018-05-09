package com.bon.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bon.common.domain.base.ResultBody;
import com.bon.wx.domain.dto.UserDTO;
import com.bon.wx.domain.entity.User;
import com.bon.wx.service.GenerateService;
import com.bon.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @program: bon-dubbo
 * @description: 用户管理模块
 * @author: Bon
 * @create: 2018-04-27 18:16
 **/
@Api(value = "创建数据库",description = "导入excel（xls）模式")
@RestController
@RequestMapping("/generate")
public class GenerateController {

    @Reference
    private GenerateService generateService;

    @ApiOperation(value = "文件路径导入")
    @ApiResponse(code = 200, message = "success")
    @GetMapping("/path")
    public ResultBody generateByFilePath(@RequestParam String path){
        generateService.generateByFilePath(path);
        return new ResultBody();
    }

}
