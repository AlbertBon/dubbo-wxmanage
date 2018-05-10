
package com.bon.wx.exception;


import com.bon.common.domain.base.ExceptionType;

/**
 * 业务异常（放）
 *
 * @author Bon
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

//    private int level;

    public BusinessException(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

//    public int getLevel() {
//        return level;
//    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
//                ", level=" + level +
                '}';
    }
}
