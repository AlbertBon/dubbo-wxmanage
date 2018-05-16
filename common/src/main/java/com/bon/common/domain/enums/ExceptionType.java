package com.bon.common.domain.enums;

/**
 * Enum 响应类型
 */
public enum ExceptionType {
    SUCCESS("00", "success"),
    DATA_ERROR("400003", "数据错误"),
    REQUEST_ERROR("400002", "请求信息错误"),
    LOGIN_ERROR("400004", "登录错误"),
    SYSTEM_ERROR("400001", "网络异常");

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


