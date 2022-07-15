package com.tang.boot.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于spring加载完成后，找到项目中是否有开启缓存的注解@EnableCaching
 * @date 2021-09-22 20:52
 * @author tangwenkai
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 判断根容器为Spring容器，防止出现调用两次的情况（mvc加载也会触发一次）
        if(event.getApplicationContext().getParent()!=null){
            //得到所有被@EnableCaching注解修饰的类
            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(EnableCaching.class);
            if(beans != null && !beans.isEmpty()) {
                CacheServiceAspect cacheService = (CacheServiceAspect)event.getApplicationContext().getBean("cacheServiceAspect");
                cacheService.setCacheEnable(true);
            }
        }
    }
}
