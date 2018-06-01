package com.bon.web.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bon.common.config.Constants;
import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.ResultBody;
import com.bon.common.service.RedisService;
import com.bon.common.util.PropertyUtil;
import com.bon.wx.exception.BusinessException;
import com.bon.wx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Set;

/**
 * @program: dubbo-wxmanage
 * @description: 拦截器
 * @author: Bon
 * @create: 2018-05-10 15:35
 **/
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        if(request.getRequestedSessionId()==null){
//            return true;
//        }
        //请求错误拦截
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin",PropertyUtil.getProperty("corsHost"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        if (request.getServletPath().equals("/error")) {
            OutputStream out = response.getOutputStream();
            out.write(new ResultBody(ExceptionType.REQUEST_ERROR).toErrString().getBytes("utf-8"));
            out.close();
            return false;
        }

        //登录验证信息拦截
        if((request.getServletPath().contains("/login/")||request.getServletPath().contains("/user/addUser"))
                &&!request.getServletPath().contains("/login/loginOut")){//不拦截/login部分,暂时不拦截新增用户
            return true;
        }
        if (request.getParameter("token") != null) {
            String token = request.getParameter("token");
            String key = MessageFormat.format(Constants.RedisKey.TOKEN_USERNAME_TOKEN,"*",token);
            if (!loginService.check(key)) {
                OutputStream out = response.getOutputStream();
                out.write(new ResultBody(ExceptionType.EXPIRED_ERROR).toErrString().getBytes("utf-8"));
                out.close();
                return false;
            }
        } else {
            String sessionId = request.getSession().getId();
            String key = MessageFormat.format(Constants.RedisKey.LOGIN_USERNAME_SESSION_ID,"*",sessionId);
            if (!loginService.check(key)) {
                OutputStream out = response.getOutputStream();
                out.write(new ResultBody(ExceptionType.EXPIRED_ERROR).toErrString().getBytes("utf-8"));
                out.close();
                return false;
            }
        }

        return true;    //如果false，停止流程，api被拦截
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle被调用");
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion被调用");
        if (e != null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin",PropertyUtil.getProperty("corsHost"));
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
            OutputStream out = response.getOutputStream();
            /*异常拦截*/
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                out.write(new ResultBody(businessException.getCode(), businessException.getMessage()).toErrString().getBytes("utf-8"));
                out.close();
            } else if (e instanceof ClassCastException) {
                out.write(new ResultBody(ExceptionType.SYSTEM_ERROR).toErrString().getBytes("utf-8"));
                out.close();
            } else if(e instanceof RedisConnectionFailureException){
                out.write(new ResultBody(ExceptionType.REDIS_ERROR).toErrString().getBytes("utf-8"));
                out.close();
            } else {
                out.write(new ResultBody(ExceptionType.SYSTEM_ERROR).toErrString().getBytes("utf-8"));
                out.close();
            }
        }
    }
}