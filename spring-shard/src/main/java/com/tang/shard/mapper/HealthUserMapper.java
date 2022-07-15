package com.tang.shard.mapper;

import com.tang.shard.mapper.entity.HealthUser;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HealthUserMapper extends BaseMapper<HealthUser,Long> {
}

