package com.tang.shard;

import com.tang.shard.service.HealthUserService;
import org.apache.shardingsphere.spi.database.metadata.DataSourceMetaData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@MapperScan(basePackages = {"com.tang.shard.mapper.repository"})
class SpringShardApplicationTests {

	@Resource
	private DataSource dataSource;
	@Test
	void contextLoads() throws SQLException {
		System.out.println(dataSource.getConnection().toString());
	}

	@Resource
	private HealthUserService userService;

	@Test
	public void testProcessUsers() throws SQLException {
		userService.processUsers();
	}
}
