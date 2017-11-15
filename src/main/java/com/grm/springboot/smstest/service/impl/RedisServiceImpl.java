package com.grm.springboot.smstest.service.impl;

import com.grm.springboot.smstest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author grm
 */

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void set(String key, Object value) {
        ValueOperations valueOperations=redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    @Override
    public Object get(String key) {
        ValueOperations valueOperations=redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void expire(String key,Long timeout) {
        redisTemplate.expire(key,timeout,TimeUnit.SECONDS);
    }

    @Override
    public Long incr(String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(key,1);
    }

    @Override
    public void del(String key){
        redisTemplate.delete(key);
    }

    @Override
    public void set(String key, String value, Long timeout) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,timeout,TimeUnit.SECONDS);
    }
}
