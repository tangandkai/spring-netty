package com.tang.shard.service.impl;

import com.tang.shard.mapper.entity.HealthUser;
import com.tang.shard.mapper.HealthUserMapper;
import com.tang.shard.service.HealthUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthUserServiceImpl implements HealthUserService {

    @Resource
    private HealthUserMapper userMapper;

    @Override
    public void processUsers() throws SQLException {
        insertUsers();
    }

    private List<Long> insertUsers() throws SQLException {
        List<Long> result = new ArrayList<>(10);
        for (Long i = 1L; i <= 10; i++) {
            HealthUser user = new HealthUser();
            user.setUserId(i);
            user.setUserName("user_" + i);
            userMapper.addEntity(user);
            result.add(user.getUserId());
            System.out.println("Insert User:" + user.getUserId());

        }
        return result;
    }

    @Override
    public List<HealthUser> getUsers() throws SQLException {
        return userMapper.findEntities();
    }

    @Override
    public void deleteAll() throws SQLException {
        userMapper.deleteAll();
    }
}
