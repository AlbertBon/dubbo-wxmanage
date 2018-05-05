package com.bon.common.domain.base;

/**
 * Enum 响应类型
 */
public enum ExceptionType {
    SUCCESS("00", "success"),
    SYSTEM_ERROR("400001", "网络异常"),
    DATA_ERROR("400003", "数据错误");

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


