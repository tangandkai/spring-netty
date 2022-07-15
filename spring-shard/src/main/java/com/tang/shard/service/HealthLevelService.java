package com.tang.shard.service;

import java.sql.SQLException;

public interface HealthLevelService {
    public void processLevels() throws SQLException;
    public void deleteAll() throws SQLException;
}
