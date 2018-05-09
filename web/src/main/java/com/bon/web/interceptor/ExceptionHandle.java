package com.bon.interceptor;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;
import com.bon.common.domain.base.ExceptionType;
import com.bon.common.domain.base.ResultBody;
import com.bon.common.util.MyLog;
import com.bon.wx.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: dubbo-wxmanage
 * @description: 异常拦截
 * @author: Bon
 * @create: 2018-05-02 11:07
 **/
@RestControllerAdvice
public class ExceptionHandle {

    private static final MyLog LOG = MyLog.getLog(ExceptionHandle.class);

    @ExceptionHandler(RpcException.class)
    @ResponseBody
    public JSONObject handle(RuntimeException e) {
        /*业务异常*/
        LOG.error(e,e.getMessage());
        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return new ResultBody(businessException.getCode(), businessException.getMessage()).toJsonObject();
        }
        return new ResultBody(ExceptionType.SYSTEM_ERROR.getCode(),ExceptionType.SYSTEM_ERROR.getMessage()).toJsonObject();
    }
}
