package com.tang.boot.mapper;

import com.tang.boot.mapper.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事务测试mapper
 * @author tangwenkai
 * @date 2021-06-03 15:12
 */
@Mapper
public interface TransactionSecondMapper extends BaseMapper<Transaction,Integer> {
}
