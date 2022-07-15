package com.tang.boot.cache;

import com.jd.jim.cli.Cluster;
import com.jd.jim.cli.ReloadableJimClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * 缓存工厂，对缓存进行操作
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 19:43
 */
@Component
@Slf4j
public class CacheFactory implements DisposableBean {

    /**
     * jimdb客户端
     */
    @Resource(name = "jimClient")
    private Cluster jimClient;

    /**
     * redis操作模板
     */
    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 从jimdb中获取数据
     * @param cacheName
     * @param key
     * @return
     */
    public String getFromJimDb(String cacheName,String key){
        String k = cacheName+"_"+key;
        try {
            if (jimClient.exists(k)){
                return jimClient.get(k);
            }
            return null;
        }catch (RedisConnectionFailureException e){
            log.error("connect JimDb error "+e.getMessage());
            return null;
        }
    }

    /**
     * 放置数据到redis
     * @param cacheName
     * @param key
     * @param value
     */
    public void putDataToJimDb(String cacheName, String key, String value, Long time, TimeUnit timeUnit) {
        String k = cacheName+"_"+key;
        try {
            //要缓存的数据不能为空
            if (null!=value && !value.equals("") && !value.trim().equals("{}")){
                if (time<=0){
                    //缓存时长小于等于0，表示不需要设置过期时间
                    jimClient.set(k,value);
                }else {
                    jimClient.setEx(k,value,time,timeUnit);
                }
            }

        } catch (RedisConnectionFailureException e) {
            //连接失败，不抛错，直接不用redis缓存了
            log.error("connect JimDb error ",e);
        }
    }

    /**
     * 从redis中将缓存数据删除
     * @param cacheName
     * @param key
     */
    public void delFromJimDb(String cacheName,String key) {
        String k = cacheName+"_"+key;
        try {
            if (jimClient.exists(k)){
                jimClient.del(k);
            }
        } catch (RedisConnectionFailureException e) {
            //连接失败，不抛错，直接不用redis缓存了
            log.error("connect JimDb error ",e.getMessage());
        }
    }


    /**
     * 从redis中获取数据
     * @param cacheName
     * @param key
     * @return
     */
    public String getFromRedis(String cacheName,String key){
        final HashOperations<String,String,String> opsForHash = redisTemplate.opsForHash();
        try {
            return opsForHash.get(cacheName, key);
        }catch (RedisConnectionFailureException e){
            log.error("connect redis error "+e.getMessage());
            return null;
        }
    }

    /**
     * 放置数据到redis
     * @param cacheName
     * @param key
     * @param value
     */
    public void putDataToRedis(String cacheName,String key,String value) {
        HashOperations<String,String,String> opsForHash = redisTemplate.opsForHash();
        try {
            //要缓存的数据不能为空
            if (null!=value && !value.equals("") && value.trim().equals("{}")){
                opsForHash.put(cacheName, key, value);
            }
        } catch (RedisConnectionFailureException e) {
            //连接失败，不抛错，直接不用redis缓存了
            log.error("connect redis error ",e);
        }
    }

    /**
     * 从redis中将缓存数据删除
     * @param cacheName
     * @param key
     */
    public void delFromRedis(String cacheName,String key) {
        HashOperations<String,String,String> opsForHash = redisTemplate.opsForHash();
        try {
            //如果key为空，直接根据缓存名删除
            if(StringUtils.isEmpty(key)) {
                redisTemplate.delete(cacheName);
            } else {
                opsForHash.delete(cacheName,key);
            }
        } catch (RedisConnectionFailureException e) {
            //连接失败，不抛错，直接不用redis缓存了
            log.error("connect redis error ",e.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        ReloadableJimClientFactory factory = (ReloadableJimClientFactory)jimClient;
        factory.clear();
    }
}
