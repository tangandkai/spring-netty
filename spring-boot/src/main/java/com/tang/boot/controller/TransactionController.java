package com.tang.boot.controller;

import com.tang.boot.beans.Users;
import com.tang.boot.mapper.entity.Transaction;
import com.tang.boot.services.transaction.impl.TransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@Api
@RestController
public class TransactionController {

    @Resource
    private TransactionServiceImpl service;

    @ApiOperation("Spring Boot 事务测试 REQUIRED")
    @PostMapping("/transaction_1")
    public Transaction transaction_1(@RequestBody @ApiParam("事务")Transaction transaction) throws SQLException {
        System.out.println("post===============");
        service.update(transaction);
        return transaction;
    }

    @ApiOperation("Spring Boot 事务测试 REQUIRES_NEW")
    @PostMapping("/transaction_2")
    public Transaction transaction_2(@RequestBody @ApiParam("事务")Transaction transaction) throws SQLException {
        System.out.println("post===============");
        service.update_3(transaction);
        return transaction;
    }


    @ApiOperation("Spring Boot 事务测试 NESTED")
    @PostMapping("/transaction_3")
    public Transaction transaction_3(@RequestBody @ApiParam("事务")Transaction transaction) throws SQLException {
        System.out.println("post===============");
        service.update_4(transaction);
        return transaction;
    }

}
