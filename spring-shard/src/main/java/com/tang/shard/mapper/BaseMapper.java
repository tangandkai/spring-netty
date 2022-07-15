package com.tang.shard.mapper;

import java.sql.SQLException;
import java.util.List;
public interface BaseMapper<T, P>{

    P addEntity(T entity) throws SQLException;

    void deleteEntity(P primaryKey) throws SQLException;

    List<T> findEntities() throws SQLException;

    void deleteAll() throws SQLException;
}
