package com.tang.shard.mapper;

import com.tang.shard.mapper.entity.HealthLevel;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HealthLevelMapper extends BaseMapper<HealthLevel,Long> {
}