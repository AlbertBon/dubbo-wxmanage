package com.bon.common.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: dubbo-wxmanage
 * @description: 基础数据传输对象
 * @author: Bon
 * @create: 2018-05-03 18:35
 **/
public class BaseDTO<T> implements Serializable {
    @ApiModelProperty(value = "当前页",example = "1")
    private int pageNum=1;
    @ApiModelProperty(value = "页面大小",example = "1000")
    private int pageSize=1000;
    @ApiModelProperty(value = "排序字段")
    private String orderBy;
    @ApiModelProperty(value = "是否进行count查询",example = "false",hidden = true)
    private boolean count;
    @ApiModelProperty(value = "分页合理化",example = "false",hidden = true)
    private Boolean reasonable;
    @ApiModelProperty(value = "当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果",example = "false",hidden = true)
    private Boolean pageSizeZero;
    @ApiModelProperty(value = "查询关键字",example = "{}")
    private Map<String,String> keyMap;

    private Example example;

    //根据条件创建查询模板
    public Example createExample(T t) {
        if(null!=this.getKeyMap()){
            example = new Example(t.getClass());
            for(Map.Entry<String, String> entry : keyMap.entrySet()){
                example.createCriteria().andCondition(entry.getKey(),entry.getValue());
            }
            return example;
        }else {
            return null;
        }
    }

    public Map<String, String> getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(Map<String, String> keyMap) {
        this.keyMap = keyMap;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

    public Boolean getPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
    }
}
