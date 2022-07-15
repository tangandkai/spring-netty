package com.tang.boot.services.transaction;

import com.tang.boot.mapper.entity.Transaction;

import java.sql.SQLException;

/**
 * 事务服务用例
 * @author tangwenkai
 * @date 2021-06-03 15:24
 */
public interface TransactionService {

    public boolean update(Transaction transaction) throws SQLException;

    public int addEntity(Transaction transaction) throws SQLException;
}
