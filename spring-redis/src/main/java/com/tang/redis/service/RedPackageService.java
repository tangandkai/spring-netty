package com.tang.redis.service;

/**
 * 红包发送、抢占服务
 */
public interface RedPackageService {

    /**
     * 发送红包
     * @param money 红包大小
     * @param number 红包个数
     * @return
     */
    String sendPackage(int money,int number);

    /**
     * 抢红包
     * @param redPackageKey 红包标识
     * @param userId 用户id
     * @return
     */
    String robPackage(String redPackageKey,String userId);
}
