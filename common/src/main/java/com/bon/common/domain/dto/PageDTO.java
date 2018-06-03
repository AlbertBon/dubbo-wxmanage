package com.bon.common.domain.dto;

import com.alibaba.fastjson.JSONObject;
import com.bon.common.util.StringUtils;
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
    @ApiModelProperty(value = "查询关键字,举例{\"equal_id\":\"1\",\"orMap\":\"{'equal_userId':'2','like_name':'2','in_name':'1,2,3','isNotNull':'name'}\"}", example = "{\"in:id\":\"1,2,3\",\"or:\":\"{'id=':'2','name=':'2','in:name':'1,2,3'}\"}")
    private Map<String, Object> keyMap;

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

    //根据条件创建查询模板（根据定义泛型）
    public Example createExample() {
        this.getTClass();
        if (null != this.getKeyMap()) {
            example = new Example(tClass);
            String flag = "";
            for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
                if(entry.getValue()==null||StringUtils.isBlank(entry.getValue().toString())){
                    break;
                }
                //获取标识值 or：,in: ,notIn:,isNull,isNotNull等等
                flag = entry.getKey().split("_")[0];
                String key= "";
                switch (flag) {
                    case "orMap":
                        Map<String, Object> map = JSONObject.parseObject(entry.getValue().toString(),Map.class);
                        Example example1 = new Example(tClass);
                        Example.Criteria criteria = example1.createCriteria();
                        for (Map.Entry<String,Object> en: map.entrySet()) {

                            //判断类型
                            if(en.getKey().split("_")[0].equals("isNull")){
                                criteria.andIsNull(en.getValue().toString());
                            }else if(en.getKey().split("_")[0].equals("isNotNull")){
                                criteria.andIsNotNull(en.getValue().toString());
                            }else if(en.getKey().split("_")[0].equals("in")){
                                criteria.andIn(StringUtils.camel2Underline(en.getKey().split("_")[1]),Arrays.asList(en.getValue().toString().split(",")));
                            }else if(en.getKey().split("_")[0].equals("notIn")){
                                criteria.andNotIn(StringUtils.camel2Underline(en.getKey().split("_")[1]),Arrays.asList(en.getValue().toString().split(",")));
                            }else if(en.getKey().split("_")[0].equals("equal")){
                                criteria.andCondition(StringUtils.camel2Underline(en.getKey().split("_")[1])+" =",en.getValue());
                            }else if(en.getKey().split("_")[0].equals("like")){
                                criteria.andCondition(StringUtils.camel2Underline(en.getKey().split("_")[1])+" like",en.getValue());
                            }
                        }
                        example.or(criteria);
                        break;
                    case "orEqual":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.or().andCondition(key+" =", entry.getValue());
                        break;
                    case "orLike":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.or().andCondition(key+" like", "%"+entry.getValue()+"%");
                        break;
                    case "in":
                        String strIn[] = entry.getValue().toString().split(",");
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andIn(key,Arrays.asList(strIn));
                        break;
                    case "notIn":
                        String strNotIn[] = entry.getValue().toString().split(",");
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andNotIn(key,Arrays.asList(strNotIn));
                        break;
                    case "isNull":
                        example.and().andIsNull(StringUtils.camel2Underline(entry.getValue().toString()));
                        break;
                    case "isNotNull":
                        example.and().andIsNotNull(StringUtils.camel2Underline(entry.getValue().toString()));
                        break;
                    case "equal":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+" =", entry.getValue());
                        break;
                    case "like":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+" like", "%"+entry.getValue()+"%");
                        break;
                    case "greater":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+ " >=", entry.getValue());
                        break;
                    case "less":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+ " <=", entry.getValue());
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
            for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
                if(entry.getValue()==null||StringUtils.isBlank(entry.getValue().toString())){
                    break;
                }
                //获取标识值 or：,in: ,notIn:,isNull,isNotNull等等
                flag = entry.getKey().split("_")[0];
                String key= "";
                switch (flag) {
                    case "orMap":
                        Map<String, Object> map = JSONObject.parseObject(entry.getValue().toString(),Map.class);
                        Example example1 = new Example(tClass);
                        Example.Criteria criteria = example1.createCriteria();
                        for (Map.Entry<String,Object> en: map.entrySet()) {

                            //判断类型
                            if(en.getKey().split("_")[0].equals("isNull")){
                                criteria.andIsNull(en.getValue().toString());
                            }else if(en.getKey().split("_")[0].equals("isNotNull")){
                                criteria.andIsNotNull(en.getValue().toString());
                            }else if(en.getKey().split("_")[0].equals("in")){
                                criteria.andIn(StringUtils.camel2Underline(en.getKey().split("_")[1]),Arrays.asList(en.getValue().toString().split(",")));
                            }else if(en.getKey().split("_")[0].equals("notIn")){
                                criteria.andNotIn(StringUtils.camel2Underline(en.getKey().split("_")[1]),Arrays.asList(en.getValue().toString().split(",")));
                            }else if(en.getKey().split("_")[0].equals("equal")){
                                criteria.andCondition(StringUtils.camel2Underline(en.getKey().split("_")[1])+" =",en.getValue());
                            }else if(en.getKey().split("_")[0].equals("like")){
                                criteria.andCondition(StringUtils.camel2Underline(en.getKey().split("_")[1])+" like",en.getValue());
                            }
                        }
                        example.or(criteria);
                        break;
                    case "orEqual":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.or().andCondition(key+" =", entry.getValue());
                        break;
                    case "orLike":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.or().andCondition(key+" like", "%"+entry.getValue()+"%");
                        break;
                    case "in":
                        String strIn[] = entry.getValue().toString().split(",");
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andIn(key,Arrays.asList(strIn));
                        break;
                    case "notIn":
                        String strNotIn[] = entry.getValue().toString().split(",");
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andNotIn(key,Arrays.asList(strNotIn));
                        break;
                    case "isNull":
                        example.and().andIsNull(StringUtils.camel2Underline(entry.getValue().toString()));
                        break;
                    case "isNotNull":
                        example.and().andIsNotNull(StringUtils.camel2Underline(entry.getValue().toString()));
                        break;
                    case "equal":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+" =", entry.getValue());
                        break;
                    case "like":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+" like", "%"+entry.getValue()+"%");
                        break;
                    case "greater":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+ " >=", entry.getValue());
                        break;
                    case "less":
                        key= StringUtils.camel2Underline(entry.getKey().split("_")[1]);
                        example.and().andCondition(key+ " <=", entry.getValue());
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

    public Map<String, Object> getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(Map<String, Object> keyMap) {
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
