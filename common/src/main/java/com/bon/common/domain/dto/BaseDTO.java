package com.bon.common.domain.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @program: dubbo-wxmanage
 * @description: 基础数据传输对象
 * @author: Bon
 * @create: 2018-05-03 18:35
 **/
public class BaseDTO<T> implements Serializable {

    private Example example;

    //根据单个字段条件创建查询模板
    public Example createExample(T t,String field,String value) {
        example = new Example(t.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition(field,value);
        return example;
    }

}
