package com.tang.shard.service;

import com.tang.shard.mapper.entity.HealthRecord;
import com.tang.shard.mapper.entity.HealthUser;

import java.sql.SQLException;
import java.util.List;

public interface HealthRecordService {
    public void processHealthRecords() throws SQLException;
    public List<HealthRecord> getRecords() throws SQLException;
    public void deleteAll() throws SQLException;
}
