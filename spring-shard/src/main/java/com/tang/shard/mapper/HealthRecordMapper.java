package com.tang.shard.mapper;

import com.tang.shard.mapper.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord,Long> {
}
