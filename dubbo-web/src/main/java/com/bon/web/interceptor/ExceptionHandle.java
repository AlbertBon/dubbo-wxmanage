package com.bon.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.bon.api.exception.BusinessException;
import com.bon.common.constant.ExceptionType;
import com.bon.common.constant.ResultBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: dubbo-wxmanage
 * @description: 异常拦截
 * @author: Bon
 * @create: 2018-05-02 11:07
 **/
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JSONObject handle(Exception e) {
        /*业务异常*/
        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return new ResultBody(businessException.getCode(), businessException.getMessage()).toJsonObject();
        }
        return new ResultBody(ExceptionType.SYSTEM_ERROR.getCode(),ExceptionType.SYSTEM_ERROR.getMessage()).toJsonObject();
    }
}
