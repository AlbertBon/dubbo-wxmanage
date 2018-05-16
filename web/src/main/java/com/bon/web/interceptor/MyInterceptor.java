package com.bon.web.interceptor;

import com.bon.common.domain.enums.ExceptionType;
import com.bon.common.domain.vo.ResultBody;
import com.bon.common.service.impl.TokenManagerImpl;
import com.bon.wx.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @program: dubbo-wxmanage
 * @description: 拦截器
 * @author: Bon
 * @create: 2018-05-10 15:35
 **/
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenManagerImpl manager;

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if(request.getServletPath().equals("/error")){
            OutputStream out = response.getOutputStream();
            out.write(new ResultBody(ExceptionType.REQUEST_ERROR).toErrString().getBytes("utf-8"));
            out.close();
            return false;
        }

//        /*token权限*/
//        // 如果不是映射到方法直接通过
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod ();
//        // 从 header 中得到 token
//        String authorization = request.getHeader (Constants.AUTHORIZATION);
//        // 验证 token
//        TokenModel model = manager.getToken (authorization);
//        if (manager.checkToken (model)) {
//            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
//            request.setAttribute (Constants.CURRENT_USER_ID, model.getUserId ());
//            return true;
//        }
//        // 如果验证 token 失败，并且方法注明了 Authorization，返回错误
//        if (method.getAnnotation (Authorization.class) != null) {
//            OutputStream out = response.getOutputStream();
//            out.write(new ResultBody(ExceptionType.LOGIN_ERROR).toErrString().getBytes("utf-8"));
//            out.close();
//            return false;
//        }


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
            OutputStream out = response.getOutputStream();
            /*异常拦截*/
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                out.write(new ResultBody(businessException.getCode(), businessException.getMessage()).toErrString().getBytes("utf-8"));
                out.close();
            } else if(e instanceof ClassCastException){
                out.write(new ResultBody(ExceptionType.SYSTEM_ERROR).toErrString().getBytes("utf-8"));
                out.close();
            }else {
                out.write(new ResultBody(ExceptionType.SYSTEM_ERROR).toErrString().getBytes("utf-8"));
                out.close();
            }
        }
    }
}