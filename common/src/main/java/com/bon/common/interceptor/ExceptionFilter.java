package com.bon.common.interceptor;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.bon.common.domain.base.ExceptionType;
import com.bon.common.domain.base.ResultBody;
import com.bon.common.domain.base.exception.BusinessException;
import com.bon.common.util.MyLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: dubbo-wxmanage
 * @description:
 * @author: Bon
 * @create: 2018-05-10 11:37
 **/
//@Activate(group = Constants.CONSUMER ,order = -1000)
public class ExceptionFilter implements Filter {
    private static final MyLog LOG = MyLog.getLog(ExceptionFilter.class);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        try {
            result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                Throwable e = result.getException();
//                String data = String.format("\r\n[level]:Error，[createTime]:%s，[platform]:%s，[serviceName]:%s，[methodName]:%s，[inputParam]:%s", DateUtil.formatDateTime(new Date()), PlatformNameEnum.PAY, invoker.getInterface().getName(), invocation.getMethodName(), JSON.toJSONString(invocation.getArguments()));
//                logger.error(data, exception);
//                ResultVo resultVo = new ResultVo(false);
//                resultVo.setResultCode(PayCenterErrorCodeEnum.PAY_ERR_100000.getCode());
//                resultVo.setResultMessage(PayCenterErrorCodeEnum.PAY_ERR_100000.getMsg());
//                //出现异常，打印日志后返回错误码
//                return new RpcResult(resultVo);
                /*业务异常*/
                LOG.error(e,e.getMessage());
                if(e instanceof BusinessException){
                    BusinessException businessException = (BusinessException) e;
                    return new RpcResult(new ResultBody(businessException.getCode(), businessException.getMessage()).toJsonObject());
                }
                RpcResult rpcResult = new RpcResult(new ResultBody(ExceptionType.SYSTEM_ERROR.getCode(),ExceptionType.SYSTEM_ERROR.getMessage()));
                return new RpcResult(new ResultBody(ExceptionType.SYSTEM_ERROR.getCode(),ExceptionType.SYSTEM_ERROR.getMessage()));
            }
        } catch (RuntimeException e) {
//            String data = String.format("\r\n[level]:Error，[createTime]:%s，[platform]:%s，[serviceName]:%s，[methodName]:%s，[inputParam]:%s", DateUtil.formatDateTime(new Date()), PlatformNameEnum.PAY, invoker.getInterface().getName(), invocation.getMethodName(), JSON.toJSONString(invocation.getArguments()));
//            logger.error(data, e);
        }
        return result;
    }

//    @Override
//    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        Result result = invoker.invoke(invocation);
//        if (result.hasException()) {
//            Throwable exception = result.getException();
//            if ((exception instanceof RuntimeException)) {
//                //可能是自定义异常
//                String message = exception.getMessage();
//                if (exception.getMessage() != null) {
//                    String[] messages = message.split("#", 2);
////                    if (messages.length == 2) {
////                        try {
////                            logger.debug("message:{}", message);
////                            Class exceptionClass = Class.forName(messages[0]);
////                            try {
////                                Constructor messageConstructor = exceptionClass.getConstructor(String.class);
////                                messageConstructor.setAccessible(true);
////                                exception = (Throwable) messageConstructor.newInstance(messages[1]);
////                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
////                                try {
////                                    logger.debug(e.getMessage(), e);
////                                    Constructor constructor = exceptionClass.getConstructor();
////                                    constructor.setAccessible(true);
////                                    exception = (Throwable) constructor.newInstance();
////                                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
////                                    logger.debug(e1.getMessage(), e1);
////                                }
////                            }
////                            if (result instanceof RpcResult) {
////                                ((RpcResult) result).setException(exception);
////                            }
////                        } catch (ClassNotFoundException e) {
////                            logger.warn(e.getMessage(), e);
////                        }
////                    }
//
//                    if (result instanceof RpcResult) {
//                        BusinessException businessException = (BusinessException)exception;
//
//
//                        ((RpcResult) result).setException(businessException);
//                    }
//                }
//            }
//        }
//        return result;
//    }


//    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        RpcContext.getContext()
//                .setInvoker(invoker)
//                .setInvocation(invocation)
//                .setLocalAddress(NetUtils.getLocalHost(), 0)        // 设置本地的地址
//                .setRemoteAddress(invoker.getUrl().getHost(),       // 设置本次调用的provider端的host和端口
//                        invoker.getUrl().getPort());
//        if (invocation instanceof RpcInvocation) {
//            ((RpcInvocation)invocation).setInvoker(invoker);
//        }
//        try {
//            return invoker.invoke(invocation);
//        } finally {
//            // 调用完成后清理attachments中的数据
//            RpcContext.getContext().clearAttachments();
//        }
//    }
}
