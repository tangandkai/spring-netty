package com.tang.boot.services.impl;

import com.tang.boot.cache.CacheService;
import com.tang.boot.mapper.UserMapper;
import com.tang.boot.mapper.entity.UserT;
import com.tang.boot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 16:56
 */
@Service(value = "userServiceImpl_2")
@Slf4j
public class UserServiceImpl_2 implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @CacheService(cacheName = "user",fieldKey = "#user.userName",cacheOperation= CacheService.CacheOperation.UPDATE)
//    @CachePut(value = "user", key = "#user.userName")
    public UserT saveOrUpdate(UserT user) {
        log.info("==================saveOrUpdate=============调用===========");
        if (userMapper.saveOrUpdate(user)){
            return user;
        }
        return null;
    }

    @Override
    @CacheService(cacheName = "user",fieldKey = "#name",cacheOperation= CacheService.CacheOperation.QUERY)
//    @Cacheable(value = "user",key = "#name")
    public UserT getUserByName(String name) {
        log.info("==================getUserByName=============调用===========");
        return userMapper.getUserByName(name);
    }

    @Override
//    @CacheEvict(value = "user", key = "#name")
    @CacheService(cacheName = "user",fieldKey = "#name",cacheOperation= CacheService.CacheOperation.DELETE)
    public Boolean delete(String name) {
        log.info("==================delete=============调用===========");
        return userMapper.delete(name);
    }
}
