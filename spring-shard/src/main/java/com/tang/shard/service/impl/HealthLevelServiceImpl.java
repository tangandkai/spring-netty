package com.tang.shard.service.impl;

import com.tang.shard.mapper.entity.HealthLevel;
import com.tang.shard.mapper.HealthLevelMapper;
import com.tang.shard.service.HealthLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class HealthLevelServiceImpl implements HealthLevelService {

    @Resource
    private HealthLevelMapper healthLevelMapper;

    @Override
    public void processLevels() throws SQLException {
        healthLevelMapper.addEntity(new HealthLevel(1L, "level1"));
        healthLevelMapper.addEntity(new HealthLevel(2L, "level2"));
        healthLevelMapper.addEntity(new HealthLevel(3L, "level3"));
        healthLevelMapper.addEntity(new HealthLevel(4L, "level4"));
        healthLevelMapper.addEntity(new HealthLevel(5L, "level5"));
    }

    @Override
    public void deleteAll() throws SQLException {
        healthLevelMapper.deleteAll();
    }
}
