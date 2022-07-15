package com.tang.boot.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 自定义注解类
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 18:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheService {

    /**
     * 缓存名称
     * @return
     */
    String cacheName();

    /**
     * 缓存key
     * @return
     */
    String fieldKey();

    /**
     * 缓存时长
     * @return
     */
    long expireTime() default 3600L;

    /**
     * 缓存时间类型
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 缓存操作类型
     * @return
     */
    CacheOperation cacheOperation();

    /**
     * 缓存操作类型
     */
    enum CacheOperation{
        QUERY,//查询
        UPDATE,//修改
        DELETE;//删除
    }
}
