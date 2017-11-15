package com.grm.springboot.smstest.service;

/**
 * @author grm
 */
public interface RedisService {

    void set(String key,Object value);

    Object get(String key);

    void expire(String key,Long timeout);

    Long incr(String key);

    void del(String key);

    void set(String key,String value,Long timeout);

}
