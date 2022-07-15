package com.tang.shard.service.impl;

import com.tang.shard.mapper.entity.HealthRecord;
import com.tang.shard.mapper.entity.HealthTask;
import com.tang.shard.mapper.HealthRecordMapper;
import com.tang.shard.mapper.HealthTaskMapper;
import com.tang.shard.service.HealthRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    @Resource
    private HealthRecordMapper recordMapper;

    @Resource
    private HealthTaskMapper taskMapper;

    @Override
    public void processHealthRecords() throws SQLException {
        insertHealthRecords();
    }

    @Override
    public List<HealthRecord> getRecords() throws SQLException {
        return recordMapper.findEntities();
    }

    private List<Long> insertHealthRecords() throws SQLException {
        List<Long> result = new ArrayList<>(10);
        for (Long i = 1L; i <= 10; i++) {
            HealthRecord healthRecord = insertHealthRecord(i);
            insertHealthTask(i, healthRecord);
            result.add(healthRecord.getRecordId());
        }
        return result;
    }

    private HealthRecord insertHealthRecord(final Long i) throws SQLException {
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setUserId(i);
        healthRecord.setLevelId(i % 5);
        healthRecord.setRemark("Remark" + i);
        recordMapper.addEntity(healthRecord);
        return healthRecord;
    }

    private void insertHealthTask(final Long i, final HealthRecord healthRecord) throws SQLException {
        HealthTask healthTask = new HealthTask();
        healthTask.setRecordId(healthRecord.getRecordId());
        healthTask.setUserId(i);
        healthTask.setTaskName("TaskName" + i);
        taskMapper.addEntity(healthTask);
    }

    @Override
    public void deleteAll() throws SQLException {
        taskMapper.deleteAll();
        recordMapper.deleteAll();
    }
}
