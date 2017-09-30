package org.sl.util.redis.impl;

import org.apache.log4j.Logger;
import org.sl.util.redis.CacheApi;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hasee on 2017/9/30.
 */
public class RedisApi implements CacheApi {
    private Logger logger=Logger.getLogger(RedisApi.class);
    private RedisTemplate redisTemplate;


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //为redis赋值
    public boolean set(Object key, Object value) {
        try {
            ValueOperations forValue = redisTemplate.opsForValue();
            forValue.set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(Object key, Object value, long date) {
        try {
            ValueOperations forValue = redisTemplate.opsForValue();
            forValue.set(key,value,date);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setHashMap(Object HK,Object key,Object value){
        try {
            HashOperations hash = redisTemplate.opsForHash();
            hash.put(HK,key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(Object key, Object value) {
        if (redisTemplate.hasKey(key)){
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //添加key，当key已存在则返回false
    public boolean add(Object key, Object value, long date) {
        if (redisTemplate.hasKey(key)){
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key,value,date);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //判断该key是否存在
    public boolean exist(Object key) {
        return redisTemplate.hasKey(key);
    }
    //根据key获取value
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }
    //根据key删除value
    public boolean delete(Object key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
