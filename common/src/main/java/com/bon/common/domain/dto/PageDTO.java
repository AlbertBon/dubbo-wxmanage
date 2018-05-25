package com.bon.common.domain.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * @program: dubbo-wxmanage
 * @description: 基础数据传输对象
 * @author: Bon
 * @create: 2018-05-03 18:35
 **/
public class PageDTO<T> implements Serializable {
    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNum = 1;
    @ApiModelProperty(value = "页面大小", example = "1000")
    private int pageSize = 1000;
    @ApiModelProperty(value = "排序字段")
    private String orderBy;
    @ApiModelProperty(value = "是否进行count查询", example = "false", hidden = true)
    private boolean count;
    @ApiModelProperty(value = "分页合理化", example = "false", hidden = true)
    private Boolean reasonable;
    @ApiModelProperty(value = "当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果", example = "false", hidden = true)
    private Boolean pageSizeZero;
    @ApiModelProperty(value = "查询关键字,举例{\"id=\":\"1\",\"or:\":\"{'id=':'2','name=':'2','in:name':'1,2,3','isNotNull':'name'}\"}", example = "{\"in:id\":\"1,2,3\",\"or:\":\"{'id=':'2','name=':'2','in:name':'1,2,3'}\"}")
    private Map<String, String> keyMap;

    private Example example;

    private Class<T> tClass;

    private Example.Criteria criteria;

    //获取T的class类型
    public void getTClass(){
        this.tClass  = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //根据条件创建查询模板（根据定义泛型）
    public Example createExample() {
        this.getTClass();
        if (null != this.getKeyMap()) {
            example = new Example(tClass);
            String flag = "";
            for (Map.Entry<String, String> entry : keyMap.entrySet()) {
                //获取标识值 or：,in: ,notIn:,isNull,isNotNull等等
                flag = entry.getKey().split(":")[0];
                switch (flag) {
                    case "or":
                        Map<String, String> map = JSONObject.parseObject(entry.getValue(),Map.class);
                        Example example1 = new Example(tClass);
                        Example.Criteria criteria = example1.createCriteria();
                        for (Map.Entry<String,String> en: map.entrySet()) {

                            //判断类型
                            if(en.getKey().split(":")[0].equals("isNull")){
                                criteria.andIsNull(en.getValue());
                            }else if(en.getKey().split(":")[0].equals("isNotNull")){
                                criteria.andIsNotNull(en.getValue());
                            }else if(en.getKey().split(":")[0].equals("in")){
                                criteria.andIn(en.getKey().split(":")[1],Arrays.asList(en.getValue().split(",")));
                            }else if(en.getKey().split(":")[0].equals("notIn")){
                                criteria.andNotIn(en.getKey().split(":")[1],Arrays.asList(en.getValue().split(",")));
                            }else {
                                criteria.andCondition(en.getKey(),en.getValue());
                            }
                        }
                        example.or(criteria);
                        break;
                    case "in":
                        String strIn[] = entry.getValue().split(",");
                        example.and().andIn(entry.getKey().split(":")[1],Arrays.asList(strIn));
                        break;
                    case "notIn":
                        String strNotIn[] = entry.getValue().split(",");
                        example.and().andNotIn(entry.getKey().split(":")[1],Arrays.asList(strNotIn));
                        break;
                    case "isNull":
                        example.and().andIsNull(entry.getValue());
                        break;
                    case "isNotNull":
                        example.and().andIsNotNull(entry.getValue());
                        break;
                    default:
                        example.and().andCondition(entry.getKey(), entry.getValue());
                        break;
                }
            }
            return example;
        } else {
            return null;
        }
    }

    //根据条件创建查询模板(自定义泛型)
    public Example createExample(T t) {
        if (null != this.getKeyMap()) {
            example = new Example(t.getClass());
            String flag = "";
            for (Map.Entry<String, String> entry : keyMap.entrySet()) {
                //获取标识值 or：,in: ,notIn:,isNull,isNotNull等等
                flag = entry.getKey().split(":")[0];
                switch (flag) {
                    case "or":
                        Map<String, String> map = JSONObject.parseObject(entry.getValue(),Map.class);
                        Example example1 = new Example(t.getClass());
                        Example.Criteria criteria = example1.createCriteria();
                        for (Map.Entry<String,String> en: map.entrySet()) {

                            //判断类型
                            if(en.getKey().split(":")[0].equals("isNull")){
                                criteria.andIsNull(en.getValue());
                            }else if(en.getKey().split(":")[0].equals("isNotNull")){
                                criteria.andIsNotNull(en.getValue());
                            }else if(en.getKey().split(":")[0].equals("in")){
                                criteria.andIn(en.getKey().split(":")[1],Arrays.asList(en.getValue().split(",")));
                            }else if(en.getKey().split(":")[0].equals("notIn")){
                                criteria.andNotIn(en.getKey().split(":")[1],Arrays.asList(en.getValue().split(",")));
                            }else {
                                criteria.andCondition(en.getKey(),en.getValue());
                            }
                        }
                        example.or(criteria);
                        break;
                    case "in":
                        String strIn[] = entry.getValue().split(",");
                        example.and().andIn(entry.getKey().split(":")[1],Arrays.asList(strIn));
                        break;
                    case "notIn":
                        String strNotIn[] = entry.getValue().split(",");
                        example.and().andNotIn(entry.getKey().split(":")[1],Arrays.asList(strNotIn));
                        break;
                    case "isNull":
                        example.and().andIsNull(entry.getValue());
                        break;
                    case "isNotNull":
                        example.and().andIsNotNull(entry.getValue());
                        break;
                    default:
                        example.and().andCondition(entry.getKey(), entry.getValue());
                        break;
                }
            }
            return example;
        } else {
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

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    public Class<T> gettClass() {
        return tClass;
    }

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }

    public Example.Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Example.Criteria criteria) {
        this.criteria = criteria;
    }
}
