package com.tang.redis.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.tang.redis.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class RedPackageServiceImpl implements RedPackageService {

    /**
     * 红包存放
     */
    private static final String RED_PACKAGE_KEY = "red_package_key:";

    /**
     * 红包消费
     */
    private static final String RED_PACKAGE_CONSUME = "red_package_consume:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String sendPackage(int money, int number) {
        // 红包切分
        Integer[] packageSplits = redPackageSplitAlgorithm(money, number);
        String key = RED_PACKAGE_KEY+IdUtil.randomUUID();
        redisTemplate.opsForList().leftPushAll(key,packageSplits);
        redisTemplate.expire(key,1, TimeUnit.DAYS);
        return "用户成功发送红包: "+key+" 红包内容为: "+Arrays.asList(packageSplits);
    }

    @Override
    public String robPackage(String redPackageKey, String userId) {
        // 判断当前用户是否已经抢过红包
        String key = RED_PACKAGE_CONSUME+redPackageKey;
        Object o = redisTemplate.opsForHash().get(key, userId);
        if (Objects.isNull(o)){
            Object value = redisTemplate.opsForList().rightPop(RED_PACKAGE_KEY + redPackageKey);
            if (Objects.nonNull(value)){
                redisTemplate.opsForHash().put(key,userId,value);
                return "用户: "+userId+"抢到红包: "+redPackageKey+" 大小为: "+value;
            }
            return "errorCode -2, 当前红包: "+redPackageKey+"已抢完";
        }
        return "errorCode -1, 当前用户: "+userId+" 已抢过红包: "+redPackageKey;
    }

    private Integer[] redPackageSplitAlgorithm(int money,int number){
        Integer[] redPackage = new Integer[number];
        int usedMoney = 0;
        for (int i=0;i<number;i++){
            if (i==number-1){
                redPackage[i] = money-usedMoney;
            }else {
                int currentMoney = ((money-usedMoney)/(number-i))*2;
                redPackage[i] = 1+RandomUtil.randomInt(0,currentMoney-1);
            }
            usedMoney+=redPackage[i];
        }
        return redPackage;
    }

    public static void main(String[] args) {
        RedPackageServiceImpl redPackageService = new RedPackageServiceImpl();
        Integer[] integers = redPackageService.redPackageSplitAlgorithm(100, 3);
        System.out.println(Arrays.asList(integers));
    }
}
