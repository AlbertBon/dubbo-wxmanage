package com.bon.common.domain.enums;

/**
 * Enum 响应类型
 */
public enum ExceptionType {
    SUCCESS("00", "success"),
    DATA_ERROR("100003", "数据错误"),
    REQUEST_ERROR("100002", "请求信息错误"),

    //登录模块
    LOGIN_AUTHORITY_ERROR("100004", "登录权限错误"),
    USERNAME_OR_PASSWORD_ERROR("100005","用户名或密码错误"),
    VALIDATE_CODE_ERROR("100006","验证码错误"),

    //通用异常
    SYSTEM_ERROR("100001", "网络异常");

    private String code;
    private String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


