package com.tang.boot.services.transaction.impl;

import com.tang.boot.mapper.TransactionFirstMapper;
import com.tang.boot.mapper.TransactionSecondMapper;
import com.tang.boot.mapper.entity.Transaction;
import com.tang.boot.services.transaction.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 *
 * @author tangwenkai
 * @date 2021-06-03 15:29
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private TransactionFirstMapper firstMapper;

    @Resource
    private TransactionSecondMapper secondMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,timeout = 1,rollbackFor = {Exception.class})   //事务注解可以添加到类上或者方法上
    public boolean update(Transaction transaction) throws SQLException {
        boolean b = firstMapper.update(transaction);
        boolean b1 = update_2(transaction);
        return b&&b1;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ,timeout = 1,rollbackFor = {Exception.class})   //事务注解可以添加到类上或者方法上
    public boolean update_3(Transaction transaction) throws SQLException {
        boolean b = firstMapper.update(transaction);
        boolean b1 = update_2(transaction);
        return b&&b1;
    }

    @Transactional(propagation = Propagation.NESTED,isolation = Isolation.REPEATABLE_READ,timeout = 1,rollbackFor = {Exception.class})   //事务注解可以添加到类上或者方法上
    public boolean update_4(Transaction transaction) throws SQLException {
        boolean b = firstMapper.update(transaction);
        boolean b1 = update_2(transaction);
        return b&&b1;
    }

    @Override
    public int addEntity(Transaction transaction) throws SQLException {
        secondMapper.addEntity(transaction);
        return firstMapper.addEntity(transaction);
    }

    public boolean update_2(Transaction transaction) throws SQLException {
        boolean b = secondMapper.update(transaction);
        return b;
    }
}
