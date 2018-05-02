package com.bon.common.constant;

/**
 * Enum 响应类型
 */
public enum ExceptionType {
    SUCCESS("00", "success"),
    DATA_ERROR("400003", "数据错误"),
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


