package com.tang.shard.mapper;

import com.tang.shard.mapper.entity.HealthTask;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HealthTaskMapper extends BaseMapper<HealthTask,Long> {
}

