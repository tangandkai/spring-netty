package com.tang.shard.service;

import com.tang.shard.mapper.entity.HealthUser;

import java.sql.SQLException;
import java.util.List;

public interface HealthUserService {

    public void processUsers() throws SQLException;

    public List<HealthUser> getUsers() throws SQLException;

    public void deleteAll() throws SQLException;
}
