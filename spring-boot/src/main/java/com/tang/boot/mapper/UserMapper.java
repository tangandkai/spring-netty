package com.tang.boot.mapper;

import com.tang.boot.mapper.entity.UserT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mapper
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 16:59
 */
@Mapper
public interface UserMapper {

    Boolean saveOrUpdate(UserT user);

    UserT getUserByName(@Param("userName") String userName);

    Boolean delete(@Param("userName") String userName);
}
