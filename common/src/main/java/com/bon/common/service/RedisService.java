package com.bon.common.service;

import java.util.List;
import java.util.Set;

/**
 * @program: dubbo-wxmanage
 * @description: redis接口
 * @author: Bon
 * @create: 2018-05-16 17:04
 **/
public interface RedisService {
    public boolean set(String key, String value);

    public void create(String key,String value);

    public void createAndExpire(String key,String value,long expire);

    public String get(String key);

    public Set<String> keys(String pattern);

    public void removeByPattern(String pattern);

    public boolean expire(String key,long expire);

    public <T> boolean setList(String key ,List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key,Object obj);

    public long rpush(String key,Object obj);

    public String lpop(String key);

    public void del(String key);
}
