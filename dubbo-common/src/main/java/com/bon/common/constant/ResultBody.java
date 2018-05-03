package com.bon.common.constant;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ResultBody<T> implements Serializable {

    private static final long serialVersionUID = 3997124446365032582L;

    /**
     * 返回码
     */
//    @ApiModelProperty(value = "返回码", required = true)
    private String code = "00";

    /**
     * 消息提示
     */
//    @ApiModelProperty(value = "消息提示")
    private String msg="success";

    /**
     * 数据
     */
//    @ApiModelProperty(value = "数据", required = true)
    private T data;

    public ResultBody(){}

    public ResultBody(String msg) {
        super();
        this.msg = msg;
    }

    public ResultBody(T data) {
        super();
        this.data = data;
    }

    public ResultBody(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResultBody(String code, String msg, T data) {
        super();
        this.code = (null==code) ? "00" : code;
        this.msg = (null==code) ? "success" : msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", getCode());
        jsonObject.put("message", getMsg());
        jsonObject.put("content", getData());
        return jsonObject.toJSONString();
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", getCode());
        jsonObject.put("message", getMsg());
        jsonObject.put("content", getData());
        return jsonObject;
    }

    public static JSONObject toJsonObject(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", ExceptionType.SUCCESS.getCode());
        jsonObject.put("message", ExceptionType.SUCCESS.getMessage());
        jsonObject.put("content", data);
        return jsonObject;
    }
}
