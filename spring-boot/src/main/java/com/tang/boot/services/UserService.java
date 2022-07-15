package com.tang.boot.services;

import com.tang.boot.mapper.entity.UserT;

/**
 * 用户服务
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 16:52
 */
public interface UserService {

    /**
     * 删除
     * @param user 用户对象
     * @return
     */
    UserT saveOrUpdate(UserT user);

    /**
     * 查询
     * @param name 名字
     * @return
     */
    UserT getUserByName(String name);

    /**
     * 删除
     * @param name 名字
     */
    Boolean delete(String name);
}
