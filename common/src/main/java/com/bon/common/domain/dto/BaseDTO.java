package com.bon.common.domain.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bon.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @program: dubbo-wxmanage
 * @description: 基础数据传输对象
 * @author: Bon
 * @create: 2018-05-03 18:35
 **/
public class BaseDTO<T> implements Serializable {

    @ApiModelProperty(value = "查询模板", hidden = true)
    private Example example;

    @ApiModelProperty(value = "泛型类型", hidden = true)
    private Class<T> tClass;

    @ApiModelProperty(value = "模板条件", hidden = true)
    private Example.Criteria criteria;

    //获取T的class类型
    public void getTClass(){
        this.tClass  = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //创建模板
    public Example createExample() {
        this.getTClass();
        example = new Example(tClass);
        criteria = example.createCriteria();
        return example;
    }

    //根据传入类型创建模板
    public Example createExample(T t) {
        example = new Example(t.getClass());
        criteria = example.createCriteria();
        return example;
    }

    //根据单个字段条件创建查询模板（定义类型方式）
    public Example andFind(String field,String value) {
        if(this.example==null){
            this.createExample();
        }
        //驼峰转下划线
        field = StringUtils.camel2Underline(field);
        criteria.andCondition(field+"=",value);
        return example;
    }

    //根据单个字段条件创建查询模板(传入类型方式)
    public Example andFind(T t,String field,String value) {
        if(this.example==null){
            this.createExample(t);
        }
        //驼峰转下划线
        field = StringUtils.camel2Underline(field);
        criteria.andCondition(field+"=",value);
        return example;
    }

    //根据单个字段条件创建模糊查询模板（定义类型方式）
    public Example.Criteria likeFind(String field,String value) {
        if(this.example==null){
            this.createExample();
        }
        //驼峰转下划线
        field = StringUtils.camel2Underline(field);
        criteria.andCondition(field+" like",value);
        return criteria;
    }

    //根据单个字段条件创建模糊查询模板(传入类型方式)
    public Example likeFind(T t,String field,String value) {
        if(this.example==null){
            this.createExample(t);
        }
        //驼峰转下划线
        field = StringUtils.camel2Underline(field);
        criteria.andCondition(field+" like",value);
        return example;
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
