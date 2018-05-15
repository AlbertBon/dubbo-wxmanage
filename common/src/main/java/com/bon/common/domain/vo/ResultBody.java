package com.bon.common.domain.vo;


import com.alibaba.fastjson.JSONObject;
import com.bon.common.domain.enums.ExceptionType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultBody<T> implements Serializable {

    private static final long serialVersionUID = 3997124446365032582L;

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码", required = true)
    private String code = "00";

    /**
     * 消息提示
     */
    @ApiModelProperty(value = "消息提示")
    private String message="success";

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", required = true)
    private T data = (T) "-";

    public ResultBody(){}

    public ResultBody(String message) {
        super();
        this.message = message;
    }

    public ResultBody(T data) {
        super();
        if(data == null){
            this.data = (T) "-";
        }else{
            this.data = data;
        }
    }

    public ResultBody(ExceptionType exceptionType) {
        super();
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }

    public ResultBody(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResultBody(String code, String message, T data) {
        super();
        this.code = (null==code) ? "00" : code;
        this.message = (null==code) ? "success" : message;
        if(data == null){
            this.data = (T) "-";
        }else{
            this.data = data;
        }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toErrString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", getCode());
        jsonObject.put("message", getMessage());
        return jsonObject.toJSONString();
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", getCode());
        jsonObject.put("message", getMessage());
        jsonObject.put("data", getData());
        return jsonObject.toJSONString();
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", getCode());
        jsonObject.put("message", getMessage());
//        jsonObject.put("data", getData());
        return jsonObject;
    }
}
